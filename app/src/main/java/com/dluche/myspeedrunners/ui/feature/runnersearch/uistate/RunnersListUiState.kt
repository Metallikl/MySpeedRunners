package com.dluche.myspeedrunners.ui.feature.runnersearch.uistate

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

//todo simplify class, removing RunnerListUiState
data class RunnersSearchUiState(
    val search: String = "",
    val listState: RunnersListUiState = RunnersListUiState.Initial
){
    sealed class RunnersListUiState {
        object Initial: RunnersListUiState()
        object Loading: RunnersListUiState()
        data class Error(val message: String): RunnersListUiState()
        data class Success(val runners: Flow<PagingData<RunnerCard>> = flowOf()): RunnersListUiState()
    }
}

