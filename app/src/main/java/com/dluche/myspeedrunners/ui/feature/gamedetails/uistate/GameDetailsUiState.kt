package com.dluche.myspeedrunners.ui.feature.gamedetails.uistate

import com.dluche.myspeedrunners.domain.model.game.Game

sealed interface GameDetailsUiState{
    data object Loading : GameDetailsUiState
    data class Success(val game: Game) : GameDetailsUiState
    data class Error(val message: String) : GameDetailsUiState
}