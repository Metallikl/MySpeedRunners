package com.dluche.myspeedrunners.ui.feature.runnerrunslist.uistate

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import kotlinx.coroutines.flow.MutableStateFlow

data class RunnerRunsListUiState(
    val runnerState: RunnerState = RunnerState.Loading,
    val search: String = "",
    val runs: MutableStateFlow<PagingData<Run>> = MutableStateFlow(PagingData.empty()),
    val personalBest: PersonalBestState = PersonalBestState.Loading,
    val gamesState: GamesFilterState = GamesFilterState.Loading,
    val selectedGame: Game? = null
) {
    sealed class RunnerState {
        data object Loading : RunnerState()
        data object Error : RunnerState()
        data class Success(val runnerCard: RunnerCard) : RunnerState()
    }

    sealed class GamesFilterState {
        data object Loading : GamesFilterState()
        data object Error : GamesFilterState()
        data class Success(val games: List<Game>) : GamesFilterState()
    }

    sealed class PersonalBestState {
        data object Loading : PersonalBestState()
        data object Error : PersonalBestState()
        data class Success(val runs: List<Run>) : PersonalBestState()
    }
}

