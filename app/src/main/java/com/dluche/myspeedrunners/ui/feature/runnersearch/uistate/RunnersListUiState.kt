package com.dluche.myspeedrunners.ui.feature.runnersearch.uistate

import com.dluche.myspeedrunners.domain.model.runner.RunnerCard

data class RunnersSearchUiState(
    val search: String = "",
    val listState: RunnersListUiState = RunnersListUiState.Initial
){
    sealed class RunnersListUiState {
        object Initial: RunnersListUiState()
        object Loading: RunnersListUiState()
        data class Error(val message: String): RunnersListUiState()
        data class Success(val runners: List<RunnerCard>): RunnersListUiState()
    }
}

