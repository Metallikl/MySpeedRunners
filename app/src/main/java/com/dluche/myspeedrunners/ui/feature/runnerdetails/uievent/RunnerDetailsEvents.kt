package com.dluche.myspeedrunners.ui.feature.runnerdetails.uievent

sealed interface RunnerDetailsEvents {
    data object RefreshFullContent: RunnerDetailsEvents
    data object RunsRetry: RunnerDetailsEvents
    data object GamesRetry: RunnerDetailsEvents
}