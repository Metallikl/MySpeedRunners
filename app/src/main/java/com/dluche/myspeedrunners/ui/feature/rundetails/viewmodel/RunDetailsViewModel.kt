package com.dluche.myspeedrunners.ui.feature.rundetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.EmbedParams.Companion.CATEGORY
import com.dluche.myspeedrunners.domain.model.common.EmbedParams.Companion.GAMES
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.usecase.run.GetRunByIdUseCase
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes
import com.dluche.myspeedrunners.ui.feature.rundetails.uievents.RunDetailsEvents
import com.dluche.myspeedrunners.ui.feature.rundetails.uistate.RunDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunDetailsViewModel @Inject constructor(
    private val getRunByIdUseCase: GetRunByIdUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var runId: String = savedStateHandle.toRoute<MySpeedRunnersRoutes.RunDetails>().runId
    private val _uiState = MutableStateFlow<RunDetailsUiState>(RunDetailsUiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun dispatchEvent(event: RunDetailsEvents) {
        when (event) {
            RunDetailsEvents.LoadRunDetails -> fetchRunDetails()
        }

    }

    private fun fetchRunDetails() {
        viewModelScope.launch {
            _uiState.update { RunDetailsUiState.Loading }

            getRunByIdUseCase(
                runId,
                EmbedParams(GAMES, CATEGORY)
            )
                .onSuccess {
                    handleRunSuccess(it)
                }.onFailure {
                    handleRunError(it)
                }
        }
    }

    private fun handleRunSuccess(run: Run) {
        _uiState.value = RunDetailsUiState.Success(run)
    }

    private fun handleRunError(throwable: Throwable) {
        _uiState.value = RunDetailsUiState.Error(throwable.message.orEmpty())
    }
}