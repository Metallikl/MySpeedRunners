package com.dluche.myspeedrunners.ui.feature.runnerrunslist.uistate

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.model.run.Run
import kotlinx.coroutines.flow.MutableStateFlow

data class RunnerRunsListUiState(
    val search: String = "",
    val runs: MutableStateFlow<PagingData<Run>> = MutableStateFlow(PagingData.empty()),
)

