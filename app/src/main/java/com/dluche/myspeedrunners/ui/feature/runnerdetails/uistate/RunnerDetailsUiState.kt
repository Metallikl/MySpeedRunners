package com.dluche.myspeedrunners.ui.feature.runnerdetails.uistate

import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.runner.Runner

data class RunnerDetailsUiState(
    val headerState: HeaderState = HeaderState.Loading,
    val runsState: RunsState = RunsState.Loading,
    val gamesState: GamesState = GamesState.Loading
) {
    sealed class HeaderState {
        data object Loading : HeaderState()
        data class Success(val runner: Runner) : HeaderState()
        data class Error(val message: String) : HeaderState()
    }

    sealed class RunsState {
        data object Loading : RunsState()
        data class Success(val runs: List<Run>) : RunsState()
        data class Error(val message: String) : RunsState()
    }

    sealed class GamesState {
        data object Loading : GamesState()
        data class Success(val games: List<Game>) : GamesState()
        data class Error(val message: String) : GamesState()
    }
}





