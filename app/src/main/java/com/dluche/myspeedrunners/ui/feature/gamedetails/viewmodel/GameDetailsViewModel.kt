package com.dluche.myspeedrunners.ui.feature.gamedetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dluche.myspeedrunners.domain.usecase.game.GetGameDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    private val getGameDetailsUseCase: GetGameDetailsUseCase,
    private val  savedStateHandle: SavedStateHandle
) : ViewModel() {

}