package com.dluche.myspeedrunners.ui.feature.gamedetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.EmbedParams.Companion.CATEGORIES
import com.dluche.myspeedrunners.domain.model.common.EmbedParams.Companion.CATEGORY
import com.dluche.myspeedrunners.domain.model.common.EmbedParams.Companion.GAMES
import com.dluche.myspeedrunners.domain.model.common.EmbedParams.Companion.MODERATORS
import com.dluche.myspeedrunners.domain.model.common.EmbedParams.Companion.PLATFORMS
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.usecase.game.GetGameDetailsUseCase
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.GameDetails
import com.dluche.myspeedrunners.ui.feature.gamedetails.uievents.GameDetailsEvents
import com.dluche.myspeedrunners.ui.feature.gamedetails.uistate.GameDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var gameId: String = savedStateHandle.toRoute<GameDetails>().gameId
    private val _uiState = MutableStateFlow<GameDetailsUiState>(GameDetailsUiState.Loading)
    val uiState: StateFlow<GameDetailsUiState> = _uiState

    fun dispatchEvent(event: GameDetailsEvents) {
        when (event) {
            GameDetailsEvents.LoadGameDetails -> fetchGameDetails()
        }

    }

    private fun fetchGameDetails() {
        viewModelScope.launch {
            _uiState.update { GameDetailsUiState.Loading }

            getGameDetailsUseCase(
                gameId,
                EmbedParams(PLATFORMS, CATEGORIES,MODERATORS)
            )
                .onSuccess {
                    handleGameSuccess(it)
                }.onFailure {
                    handleGameError(it)
                }
        }
    }

    private fun handleGameSuccess(game: Game) {
        _uiState.value = GameDetailsUiState.Success(game)
    }

    private fun handleGameError(throwable: Throwable) {
        _uiState.value = GameDetailsUiState.Error(throwable.message.orEmpty())
    }
}