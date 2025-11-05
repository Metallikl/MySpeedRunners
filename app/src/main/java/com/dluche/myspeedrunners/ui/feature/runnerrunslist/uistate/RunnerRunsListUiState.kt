package com.dluche.myspeedrunners.ui.feature.runnerrunslist.uistate

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import kotlinx.coroutines.flow.MutableStateFlow

data class RunnerRunsListUiState(
    val runnerState: RunnerState = RunnerState.Loading,
    val search: String = "",
    val runs: MutableStateFlow<PagingData<Run>> = MutableStateFlow(PagingData.empty()),
){
    sealed class RunnerState{
        data object Loading : RunnerState()
        data object Error : RunnerState()
        data class Success(val runnerCard: RunnerCard) : RunnerState()
    }
}

