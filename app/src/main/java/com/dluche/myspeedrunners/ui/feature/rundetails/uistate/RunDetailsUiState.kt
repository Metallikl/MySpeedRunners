package com.dluche.myspeedrunners.ui.feature.rundetails.uistate

import com.dluche.myspeedrunners.domain.model.run.Run

sealed interface RunDetailsUiState{
    data object Initial : RunDetailsUiState
    data object Loading : RunDetailsUiState
    data class Success(val run: Run) : RunDetailsUiState
    data class Error(val message: String) : RunDetailsUiState
}