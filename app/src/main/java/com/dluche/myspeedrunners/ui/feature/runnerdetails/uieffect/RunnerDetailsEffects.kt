package com.dluche.myspeedrunners.ui.feature.runnerdetails.uieffect

sealed interface RunnerDetailsEffects {
    data object NavigateToRunnersRunsList: RunnerDetailsEffects
    data object ErrorOnSaveRunnerCard: RunnerDetailsEffects
}
