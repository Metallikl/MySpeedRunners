package com.dluche.myspeedrunners.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.GameDetails
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.RunDetails
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.RunnerDetails
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.RunnerRunsList
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.RunnersSearch
import com.dluche.myspeedrunners.ui.feature.gamedetails.screen.GameDetailsRoute
import com.dluche.myspeedrunners.ui.feature.rundetails.screen.RunDetailsRoute
import com.dluche.myspeedrunners.ui.feature.runnerdetails.screen.RunnerDetailsRoute
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.screen.RunnerRunsListRoute
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
            val runnerId = remember {
                it.toRoute<RunnerDetails>().runnerId
            }
            RunnerDetailsRoute(
                navigateToRunDetails = { runId ->
                    navController.navigate(RunDetails(runId))
                },
                onBackClick = { navController.popBackStack() },
                navigateToGameDetails = { gameId ->
                    navController.navigate(GameDetails(gameId))
                },
                navigateToRunnerRunsList = {
                    navController.navigate(RunnerRunsList(runnerId))
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
                navigateToRunnerDetails = { runnerId ->
                    navController.navigate(RunnerDetails(runnerId))
                },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable<RunnerRunsList>{
            RunnerRunsListRoute(
                onBackClick = { navController.popBackStack() },
                navigateToRunDetails = { runId ->
                    navController.navigate(RunDetails(runId))
                }
            )
        }
    }
}