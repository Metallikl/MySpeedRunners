package com.dluche.myspeedrunners.ui.feature.runnersearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dluche.myspeedrunners.domain.usecase.runner.SearchRunnersUseCase
import com.dluche.myspeedrunners.ui.feature.runnersearch.uievent.RunnersSearchEvents
import com.dluche.myspeedrunners.ui.feature.runnersearch.uistate.RunnersSearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunnersSearchViewModel @Inject constructor(
    private val searchRunnersUseCase: SearchRunnersUseCase,
) : ViewModel() {
    private var typingJob: Job? = null

    private val _uiState = MutableStateFlow(RunnersSearchUiState())
    val uiState = _uiState.asStateFlow()

    fun dispatchEvent(event: RunnersSearchEvents) {
        when (event) {
            is RunnersSearchEvents.UpdateSearch -> updateSearch(event.search)
        }

    }

    private fun updateSearch(search: String) {
        _uiState.update { it.copy(search = search) }
        typingJob?.cancel()
        typingJob = viewModelScope.launch {
            delay(500)
            when {
                search.isEmpty() -> {
                    _uiState.value.runners.update { PagingData.empty() }
                }

                search.length >= 3 -> searchRunners(search)
            }
        }
    }

    private fun searchRunners(search: String) {
        viewModelScope.launch {
            searchRunnersUseCase(search).cachedIn(viewModelScope).collect { pagingDataRunner ->
                _uiState.value.runners.update { pagingDataRunner }
            }
        }
    }
}