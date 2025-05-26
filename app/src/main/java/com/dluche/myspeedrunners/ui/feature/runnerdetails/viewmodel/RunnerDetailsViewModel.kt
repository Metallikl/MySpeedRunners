package com.dluche.myspeedrunners.ui.feature.runnerdetails.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.usecase.game.GetRunnerGamesUseCase
import com.dluche.myspeedrunners.domain.usecase.run.GetRunnerRunsUseCase
import com.dluche.myspeedrunners.domain.usecase.runner.GetRunnerUseCase
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes
import com.dluche.myspeedrunners.ui.feature.runnerdetails.uievent.RunnerDetailsEvents
import com.dluche.myspeedrunners.ui.feature.runnerdetails.uistate.RunnerDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunnerDetailsViewModel @Inject constructor(
    private val getRunnerUseCase: GetRunnerUseCase,
    private val getRunnerRunsUseCase: GetRunnerRunsUseCase,
    private val getRunnerGamesUseCase: GetRunnerGamesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var runnerId: String = ""
    private val _uiState = MutableStateFlow(RunnerDetailsUiState())
    val uiState = _uiState.asStateFlow()

    val runnerIdList = listOf<String>(
        "kjp1v74j",//LD
        "dexs",
        "Zycko",
        "ArkhanLight",
        "Oh_my_gourdness",
        "Neczin_",
        "Movisterium",
        "Krolm",
        "TiaDaCoxinhaBR",//TiaDaCoxinhaBR img gif
        "NooTzin"//TiaDaCoxinhaBR img gif
    )

    init {
        runnerId = savedStateHandle.toRoute<MySpeedRunnersRoutes.RunnerDetails>().runnerId
        if (runnerId.isNotEmpty()) {
            fetchRunner(runnerId)
        } else {
            dispatchRandom()
        }
    }

    fun dispatchEvent(event: RunnerDetailsEvents) {
        when(event){
            RunnerDetailsEvents.RefreshFullContent -> {
                fetchRunner(runnerId)
            }

            RunnerDetailsEvents.RunsRetry -> {
                fetchRunnerRuns(runnerId)
            }

            RunnerDetailsEvents.GamesRetry -> {
                fetchRunnerGames(runnerId)
            }
        }

    }

    fun dispatchRandom() {
        runnerId = try {
            runnerIdList.random()
        } catch (e: Exception) {
            "kjp1v74j"
        }
        fetchRunner(runnerId)
    }

    private fun fetchRunner(runnerId: String) {
        _uiState.update {
            RunnerDetailsUiState()
        }

        viewModelScope.launch {
            getRunnerUseCase(runnerId)
                .fold(
                    onSuccess = { runner ->
                        handleRunnerDetailsSuccess(runner)
                    },
                    onFailure = { error ->
                        handleRunnerDetailsError(error)
                    }
                )
        }
    }

    private fun handleRunnerDetailsSuccess(runner: Runner) {
        _uiState.update { curState ->
            curState.copy(
                headerState = RunnerDetailsUiState.HeaderState.Success(runner = runner)
            )
        }
        fetchRunnerRuns(runner.id)
        fetchRunnerGames(runner.id)
    }

    private fun handleRunnerDetailsError(error: Throwable) {
        _uiState.update { curState ->
            curState.copy(
                headerState = RunnerDetailsUiState.HeaderState.Error(
                    message = error.message ?: "Unknown error"
                )
            )
        }
    }

    private fun fetchRunnerRuns(runnerId: String) {
        viewModelScope.launch {
            _uiState.update { curState ->
                curState.copy(
                    runsState = RunnerDetailsUiState.RunsState.Loading
                )
            }
            getRunnerRunsUseCase.invoke(
                runnerId,
                EmbedParams("game", "category"),
                QueryOrderBy("date", "desc")
            )
                .onSuccess { handleRunsSuccess(it.data) }
                .onFailure { handleRunsError(it) }
        }
    }

    private fun handleRunsSuccess(runs: List<Run>) {
        _uiState.update { curState ->
            curState.copy(
                runsState = RunnerDetailsUiState.RunsState.Success(runs = runs)
            )
        }
    }

    private fun handleRunsError(throwable: Throwable) {
        _uiState.update { curState ->
            curState.copy(
                runsState = RunnerDetailsUiState.RunsState.Error(
                    message = throwable.message ?: "Unknown error"
                )
            )
        }
        Log.e("RunnerDetailsViewModel", "Error fetching runner runs", throwable)
    }

    private fun fetchRunnerGames(runnerId: String) {
        _uiState.update { curState ->
            curState.copy(
                gamesState = RunnerDetailsUiState.GamesState.Loading
            )
        }
        viewModelScope.launch {
            getRunnerGamesUseCase(runnerId)
                .onSuccess { handleGamesSuccess(it) }
                .onFailure { handleGamesError(it) }
        }
    }

    private fun handleGamesSuccess(games: List<Game>){
        _uiState.update { curState ->
            curState.copy(
                gamesState = RunnerDetailsUiState.GamesState.Success(games = games)
            )
        }
    }

    private fun handleGamesError(throwable: Throwable) {
        _uiState.update { curState ->
            curState.copy(
                gamesState = RunnerDetailsUiState.GamesState.Error(
                    message = throwable.message ?: "Unknown error"
                )
            )
        }
        Log.e("RunnerDetailsViewModel", "Error fetching runner runs", throwable)
    }
}