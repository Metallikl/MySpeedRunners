package com.dluche.myspeedrunners.ui.feature.runnerdetails.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.usecase.run.GetRunnerRunsUseCase
import com.dluche.myspeedrunners.domain.usecase.runner.GetRunnerUseCase
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
) : ViewModel() {

    private val _uiState = MutableStateFlow(RunnerDetailsUiState())
    val uiState = _uiState.asStateFlow()
    val runnerIdList = listOf<String>(
        "kjp1v74j",
        "dexs",
        "Zycko",
        "ArkhanLight",
        "Oh_my_gourdness",
        "Neczin_",
        "Movisterium",
    )

    init {
        dispatchEvent()
    }

    fun dispatchEvent() {
        _uiState.update {
            RunnerDetailsUiState()
        }
        var id = try {
            runnerIdList.random()
        } catch (e: Exception) {
            "kjp1v74j"
        }
        fetchRunner(id)
    }

    private fun fetchRunner(runnerId: String) {
        viewModelScope.launch {
            getRunnerUseCase(runnerId)
                .fold(
                    onSuccess = { runner ->
                        _uiState.update { curState ->
                            curState.copy(
                                headerState = RunnerDetailsUiState.HeaderState.Success(runner = runner)
                            )
                        }
                    },
                    onFailure = { error ->
                        _uiState.update { curState ->
                            curState.copy(
                                headerState = RunnerDetailsUiState.HeaderState.Error(
                                    message = error.message ?: "Unknown error"
                                )
                            )
                        }
                    }
                )
            getRunnerRunsUseCase.invoke(
                runnerId,
                EmbedParams("game","category"),
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

}