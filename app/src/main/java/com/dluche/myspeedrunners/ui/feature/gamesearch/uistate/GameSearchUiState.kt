package com.dluche.myspeedrunners.ui.feature.gamesearch.uistate

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.model.game.GameCard
import kotlinx.coroutines.flow.MutableStateFlow

data class GameSearchUiState(
    val search: String = "",
    val games: MutableStateFlow<PagingData<GameCard>> = MutableStateFlow(PagingData.empty()),
)

