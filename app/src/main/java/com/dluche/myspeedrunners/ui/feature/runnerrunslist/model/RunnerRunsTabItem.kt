package com.dluche.myspeedrunners.ui.feature.runnerrunslist.model

import androidx.compose.ui.graphics.vector.ImageVector

data class RunnerRunsTabItem(
    val tabType: RunnerRunsTabType,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
