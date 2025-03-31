package com.dluche.myspeedrunners.ui.feature.runnerdetails.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.usecase.run.GetRunnerRunsUseCase
import com.dluche.myspeedrunners.domain.usecase.runner.GetRunnerUseCase
import com.dluche.myspeedrunners.ui.feature.runnerdetails.uistate.RunnerDetailsUiState
import com.dluche.myspeedrunners.ui.feature.runnerdetails.uistate.RunnerDetailsUiState.Success
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

    private val _uiState = MutableStateFlow<RunnerDetailsUiState>(RunnerDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()
    val runnerIdList = listOf<String>(
        "kjp1v74j",
        "ronaldo",
        "dexs",
        "Zycko",
        "ArkhanLight",
        "Oh_my_gourdness"
    )

    init {
        dispatchEvent()
    }

    fun dispatchEvent() {
        _uiState.update { RunnerDetailsUiState.Loading }
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
                        _uiState.update {
                            Success(runner = runner)
                        }
                    },
                    onFailure = { error ->
                        _uiState.update {
                            RunnerDetailsUiState.Error(message = error.message ?: "Unknown error")
                        }
                    }
                )
            getRunnerRunsUseCase.invoke(
                runnerId,
                EmbedParams("game"),
                QueryOrderBy("submitted", "desc")
            )
                .onSuccess({
                    val le = it.data
                })
                .onFailure({
                    Log.e("RunnerDetailsViewModel", "Error fetching runner runs", it)
                })
        }
    }
}