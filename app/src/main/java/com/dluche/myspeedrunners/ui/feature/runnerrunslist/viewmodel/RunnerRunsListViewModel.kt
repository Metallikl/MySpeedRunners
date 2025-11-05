package com.dluche.myspeedrunners.ui.feature.runnerrunslist.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.EmbedParams.Companion.CATEGORY
import com.dluche.myspeedrunners.domain.model.common.EmbedParams.Companion.GAMES
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy.Companion.DATE
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy.Companion.DESC
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.domain.usecase.run.SearchRunnerRunsUseCase
import com.dluche.myspeedrunners.domain.usecase.runner.GetRunnerCardUseCase
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.uievent.RunnerRunsListEvent
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.uistate.RunnerRunsListUiState
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.uistate.RunnerRunsListUiState.RunnerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunnerRunsListViewModel @Inject constructor(
    private val getRunnerCardUseCase: GetRunnerCardUseCase,
    private val searchRunnerRunsUseCase: SearchRunnerRunsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(RunnerRunsListUiState())
    val uiState: StateFlow<RunnerRunsListUiState> = _uiState
    val runnerId: String = savedStateHandle.toRoute<MySpeedRunnersRoutes.RunnerRunsList>().runnerId

    init {
        initialLoad()
    }

    fun dispatchEvent(event: RunnerRunsListEvent) {
        when (event) {
            RunnerRunsListEvent.InitialLoad -> initialLoad()
            is RunnerRunsListEvent.SearchRuns -> searchRuns(event.search)
        }
    }

    private fun initialLoad() {
        fetchRunnerInfo()
        fetchRuns()
    }

    private fun fetchRunnerInfo() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(runnerState = RunnerState.Loading)
            }
            getRunnerCardUseCase(runnerId)
                .onSuccess { runnerCard ->
                    handleRunnerCardSuccess(runnerCard)
                }.onFailure {
                    handleRunnerCardFailure()
                }
        }
    }



    private fun handleRunnerCardSuccess(runnerCard: RunnerCard) {
        _uiState.update {
            it.copy(
                runnerState = RunnerState.Success(
                    runnerCard
                )
            )
        }
    }

    private fun handleRunnerCardFailure() {
        _uiState.update {
            it.copy(
                runnerState = RunnerState.Error
            )
        }
    }

    private fun fetchRuns() {
        viewModelScope.launch {
            searchRunnerRunsUseCase(
                runnerId = runnerId,
                embedParams = EmbedParams(GAMES, CATEGORY),
                queryOrderBy = QueryOrderBy(DATE, DESC)
            ).cachedIn(viewModelScope).collect { pagingDataRuns ->
                _uiState.value.runs.update {
                    pagingDataRuns
                }
            }
        }
    }


    private fun searchRuns(search: String) {
        TODO("Not yet implemented")
    }
}