package com.dluche.myspeedrunners.ui.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.outlined.VideogameAsset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.navigation.MySpeedRunnersNavHost
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.GamesSearch
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.RunnersSearch

@ExperimentalMaterial3Api
@Composable
fun MySpeedRunnersApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            // Exibe a bottom bar apenas nas telas de busca (principais)
            val showBottomBar = currentDestination?.hierarchy?.any {
                it.hasRoute<RunnersSearch>() || it.hasRoute<GamesSearch>()
            } == true

            if (showBottomBar) {
                NavigationBar {
                    val runnerSelected =
                        currentDestination?.hierarchy?.any { it.hasRoute<RunnersSearch>() } == true
                    NavigationBarItem(
                        selected = runnerSelected,
                        onClick = {
                            navController.navigate(RunnersSearch) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (runnerSelected) Icons.AutoMirrored.Filled.DirectionsRun
                                else Icons.AutoMirrored.Outlined.DirectionsRun,
                                contentDescription = stringResource(R.string.runners_label)
                            )
                        },
                        label = { Text(stringResource(R.string.runners_label)) }
                    )

                    val gamesSelected =
                        currentDestination?.hierarchy?.any { it.hasRoute<GamesSearch>() } == true
                    NavigationBarItem(
                        selected = gamesSelected,
                        onClick = {
                            navController.navigate(GamesSearch) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (gamesSelected) Icons.Filled.VideogameAsset
                                else Icons.Outlined.VideogameAsset,
                                contentDescription = stringResource(R.string.games_label)
                            )
                        },
                        label = { Text(stringResource(R.string.games_label)) }
                    )
                }
            }
        }
    ) { innerPaddings ->
        Box(
            modifier = Modifier
                .consumeWindowInsets(innerPaddings)
                .padding(paddingValues = innerPaddings)
                .imePadding()
                .fillMaxSize()
        ) {
            MySpeedRunnersNavHost(navController = navController)
        }
    }
}
