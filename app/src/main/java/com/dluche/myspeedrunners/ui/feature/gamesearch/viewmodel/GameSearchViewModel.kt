package com.dluche.myspeedrunners.ui.feature.gamesearch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dluche.myspeedrunners.domain.usecase.game.SearchGamesUseCase
import com.dluche.myspeedrunners.extension.removeAccentsAndSpaces
import com.dluche.myspeedrunners.ui.feature.gamesearch.uievent.GameSearchEvents
import com.dluche.myspeedrunners.ui.feature.gamesearch.uievent.GameSearchEvents.UpdateSearch
import com.dluche.myspeedrunners.ui.feature.gamesearch.uistate.GameSearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameSearchViewModel @Inject constructor(
    private val searchGameUseCase: SearchGamesUseCase,
) : ViewModel() {
    private var typingJob: Job? = null

    private val _uiState = MutableStateFlow(GameSearchUiState())
    val uiState = _uiState.asStateFlow()

    fun dispatchEvent(event: GameSearchEvents) {
        when (event) {
            is UpdateSearch -> updateSearch(event.search)
        }

    }

    private fun updateSearch(search: String) {
        _uiState.update { it.copy(search = search) }
        typingJob?.cancel()
        typingJob = viewModelScope.launch {
            delay(500)
            when {
                search.isEmpty() -> {
                    _uiState.value.games.update { PagingData.empty() }
                }

                search.length >= 3 -> searchGames(search.removeAccentsAndSpaces())
            }
        }
    }

    private fun searchGames(search: String) {
        viewModelScope.launch {
            searchGameUseCase(search).cachedIn(viewModelScope).collect { pagingDataRunner ->
                _uiState.value.games.update { pagingDataRunner }
            }
        }
    }
}