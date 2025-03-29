package com.dluche.myspeedrunners.ui.feature.runnerdetails.uistate

import com.dluche.myspeedrunners.domain.model.runner.Runner

sealed class RunnerDetailsUiState {
    data object Loading : RunnerDetailsUiState()
    data class Success(val runner: Runner) : RunnerDetailsUiState()
    data class Error(val message: String) : RunnerDetailsUiState()
}