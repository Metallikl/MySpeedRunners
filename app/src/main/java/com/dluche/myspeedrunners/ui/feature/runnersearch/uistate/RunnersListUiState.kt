package com.dluche.myspeedrunners.ui.feature.runnersearch.uistate

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import kotlinx.coroutines.flow.MutableStateFlow

data class RunnersSearchUiState(
    val search: String = "",
    val runners: MutableStateFlow<PagingData<RunnerCard>> = MutableStateFlow(PagingData.empty()),
)

