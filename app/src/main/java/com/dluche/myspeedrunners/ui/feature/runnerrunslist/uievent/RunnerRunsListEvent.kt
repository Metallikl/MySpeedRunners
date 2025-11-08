package com.dluche.myspeedrunners.ui.feature.runnerrunslist.uievent

import com.dluche.myspeedrunners.domain.model.game.Game

sealed interface RunnerRunsListEvent {
    data object InitialLoad : RunnerRunsListEvent
    data class FilterByGame(val game: Game) : RunnerRunsListEvent
    data object ClearFilter: RunnerRunsListEvent
}