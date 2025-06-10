package com.dluche.myspeedrunners.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.GameDetails
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.RunDetails
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.RunnerDetails
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.RunnersSearch
import com.dluche.myspeedrunners.ui.feature.gamedetails.screen.GameDetailsRoute
import com.dluche.myspeedrunners.ui.feature.rundetails.screen.RunDetailsRoute
import com.dluche.myspeedrunners.ui.feature.runnerdetails.screen.RunnerDetailsRoute
import com.dluche.myspeedrunners.ui.feature.runnersearch.screen.RunnersSearchRoute

@Composable
fun MySpeedRunnersNavHost() {

    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = RunnersSearch) {
        composable<RunnersSearch> {
            RunnersSearchRoute(
                navigateToRunnerDetails = { runnerId ->
                    navController.navigate(RunnerDetails(runnerId))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable<RunnerDetails> {
            RunnerDetailsRoute(
                navigateToRunDetails = { runId ->
                    navController.navigate(RunDetails(runId))
                },
                onBackClick = { navController.popBackStack() },
                navigateToGameDetails = { gameId ->
                    navController.navigate(GameDetails(gameId))
                }
            )
        }

        composable<RunDetails> {
            RunDetailsRoute(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<GameDetails> {
            GameDetailsRoute(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}