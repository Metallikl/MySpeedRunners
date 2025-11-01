package com.dluche.myspeedrunners.ui.feature.runnerrunslist.uievent

sealed interface RunnerRunsListEvent {
    data object InitialLoad: RunnerRunsListEvent
    data class SearchRuns(val search:String): RunnerRunsListEvent
}