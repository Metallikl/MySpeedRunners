package com.dluche.myspeedrunners.ui.feature.runnerrunslist.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dluche.myspeedrunners.data.util.RequestConstants.CATEGORY
import com.dluche.myspeedrunners.data.util.RequestConstants.GAMES
import com.dluche.myspeedrunners.domain.QueryParams
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy.Companion.DATE
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy.Companion.DESC
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.domain.usecase.game.GetGamesFromPersonalBestUseCase
import com.dluche.myspeedrunners.domain.usecase.run.GetRunnerPersonalBestUseCase
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
    private val savedStateHandle: SavedStateHandle,
    private val getGameAsFilter: GetGamesFromPersonalBestUseCase,
    private val getPersonalBestUseCase: GetRunnerPersonalBestUseCase
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
            is RunnerRunsListEvent.FilterByGame -> filterRunsByGame(event.game)//todo aplicar filtro nos p.bs
            RunnerRunsListEvent.ClearFilter -> clearFilter()
            RunnerRunsListEvent.RunsRetry -> fetchRuns()
            RunnerRunsListEvent.LoadGameFilter -> fetchGames()
        }
    }

    private fun initialLoad() {
        fetchRunnerInfo()
        fetchRuns()
        fetchPersonalBestRuns()
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
            _uiState.value.runs.update {
                PagingData.empty()
            }

            searchRunnerRunsUseCase(
                runnerId = runnerId,
                embedParams = EmbedParams(GAMES, CATEGORY),
                queryOrderBy = QueryOrderBy(DATE, DESC),
                queryParams = getGameFilterParam()
            ).cachedIn(viewModelScope).collect { pagingDataRuns ->
                _uiState.value.runs.update {
                    pagingDataRuns
                }
            }
        }
    }

    private fun fetchPersonalBestRuns() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    personalBest = RunnerRunsListUiState.PersonalBestState.Loading
                )
            }

            getPersonalBestUseCase(
                runnerId = runnerId,
                embedParams = EmbedParams(GAMES, CATEGORY),
                queryOrderBy = null,
                gameIdFilter = _uiState.value.selectedGame?.id
            ).onSuccess {
                handlePersonalBestSuccess(it)
            }.onFailure {
                handlePersonalBestError(it)
            }
        }
    }

    private fun handlePersonalBestSuccess(bestRuns: List<Run>){
        _uiState.update {
            it.copy(
                personalBest = RunnerRunsListUiState.PersonalBestState.Success(
                     bestRuns
                )
            )
        }
    }

    private fun handlePersonalBestError(error: Throwable) {
        _uiState.update {
            it.copy(
                personalBest = RunnerRunsListUiState.PersonalBestState.Error
            )
        }
    }

    private fun getGameFilterParam(): QueryParams? {
        return _uiState.value.selectedGame?.let { game ->
            QueryParams(params = hashMapOf(GAMES to game.id))
        }
    }


    private fun fetchGames() {
        viewModelScope.launch {
            getGameAsFilter(
                runnerId = runnerId,
                embedParams = EmbedParams(GAMES, CATEGORY),
                queryOrderBy = null
            ).onSuccess {
                handleGamesAsFilterSuccess(it)
            }.onFailure {
                handleGamesAsFilterError(it)
            }
        }
    }

    private fun handleGamesAsFilterSuccess(games: List<Game>) {
        _uiState.update {
            it.copy(
                gamesState = RunnerRunsListUiState.GamesFilterState.Success(
                    games = games
                )
            )
        }
    }

    private fun handleGamesAsFilterError(error: Throwable) {
        _uiState.update {
            it.copy(
                gamesState = RunnerRunsListUiState.GamesFilterState.Error
            )
        }
    }

    private fun filterRunsByGame(game: Game) {
        _uiState.update {
            it.copy(
                selectedGame = game
            )
        }
        fetchRuns()
        fetchPersonalBestRuns()
    }

    private fun clearFilter() {
        _uiState.update {
            //it.runs.update { PagingData.empty() }
            it.copy(
                selectedGame = null,
            )
        }
        fetchRuns()
        fetchPersonalBestRuns()
    }
}