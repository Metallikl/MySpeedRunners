package com.dluche.myspeedrunners.ui.feature.gamedetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.dluche.myspeedrunners.data.util.RequestConstants.CATEGORIES
import com.dluche.myspeedrunners.data.util.RequestConstants.CATEGORY
import com.dluche.myspeedrunners.data.util.RequestConstants.GAMES
import com.dluche.myspeedrunners.data.util.RequestConstants.MODERATORS
import com.dluche.myspeedrunners.data.util.RequestConstants.PLATFORMS
import com.dluche.myspeedrunners.data.util.RequestConstants.PLAYERS
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy.Companion.DATE
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy.Companion.DESC
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.usecase.game.GetGameDetailsUseCase
import com.dluche.myspeedrunners.domain.usecase.run.GetGameRunsUseCase
import com.dluche.myspeedrunners.navigation.routes.MySpeedRunnersRoutes.GameDetails
import com.dluche.myspeedrunners.ui.feature.gamedetails.uievents.GameDetailsEvents
import com.dluche.myspeedrunners.ui.feature.gamedetails.uistate.GameDetailsUiState
import com.dluche.myspeedrunners.ui.feature.gamedetails.uistate.GameDetailsUiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val getGamesRunsUseCase: GetGameRunsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var gameId: String = savedStateHandle.toRoute<GameDetails>().gameId
    private val _uiState = MutableStateFlow<GameDetailsUiState>(GameDetailsUiState())
    val uiState: StateFlow<GameDetailsUiState> = _uiState

    fun dispatchEvent(event: GameDetailsEvents) {
        when (event) {
            GameDetailsEvents.LoadGameDetails -> fetchGameDetails()
        }

    }

    private fun fetchGameDetails() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    mainState = MainState.Loading,
                    runsState = RunsState.Loading
                )
            }

            getGameDetailsUseCase(
                gameId,
                EmbedParams(PLATFORMS, CATEGORIES, MODERATORS)
            ).onSuccess {
                handleGameSuccess(it)
            }.onFailure {
                handleGameError(it)
            }

            getGamesRunsUseCase(
                gameId,
                EmbedParams(GAMES, CATEGORY,PLAYERS),
                QueryOrderBy(DATE, DESC)
            ).onSuccess {
                handleGameRunsSuccess(it.data)
            }.onFailure {
                handleGameRunsError(it)
            }
        }
    }

    private fun handleGameSuccess(game: Game) {
        _uiState.update {
            it.copy(
                mainState = MainState.Success(game),
            )
        }
    }

    private fun handleGameError(throwable: Throwable) {
        _uiState.update {
            it.copy(
                mainState = MainState.Error(throwable.message.orEmpty()),
            )
        }
    }

    private fun handleGameRunsSuccess(runs: List<Run>) {
        _uiState.update {
            it.copy(
                runsState = RunsState.Success(runs),
            )
        }
    }

    private fun handleGameRunsError(throwable: Throwable) {
        _uiState.update {
            it.copy(
                runsState = RunsState.Error(throwable.message.orEmpty()),
            )
        }
    }
}