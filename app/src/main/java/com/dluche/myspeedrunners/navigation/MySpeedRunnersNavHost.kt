package com.dluche.myspeedrunners.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.RunnerDetails
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.RunnersSearch
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
            val runnerId = it.toRoute<RunnerDetails>().runnerId
            RunnerDetailsRoute(
                runnerId = runnerId,
                onBackClick = { navController.popBackStack() }
            )

        }
    }


}