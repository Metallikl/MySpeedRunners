package com.dluche.myspeedrunners.ui.feature.gamedetails.uistate

import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.leaderboard.Leaderboard
import com.dluche.myspeedrunners.domain.model.leaderboard.LeaderboardRun
import com.dluche.myspeedrunners.domain.model.run.Run

data class GameDetailsUiState(
    val mainState: MainState = MainState.Loading,
    val runsState: RunsState = RunsState.Loading,
    val leaderboardState: LeaderboardState = LeaderboardState.Loading
){
    sealed interface MainState {
        data object Loading : MainState
        data class Success(val game: Game) : MainState
        data class Error(val message: String) : MainState
    }

    sealed interface RunsState {
        data object Loading : RunsState
        data class Success(val runs: List<Run>) : RunsState
        data class Error(val message: String) : RunsState
    }

    sealed interface LeaderboardState {
        data object Loading : LeaderboardState
        data class Success(val runs: List<LeaderboardRun>) : LeaderboardState
        data class Error(val message: String) : LeaderboardState
    }
}