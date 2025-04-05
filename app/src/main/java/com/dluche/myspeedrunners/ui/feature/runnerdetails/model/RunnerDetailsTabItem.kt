package com.dluche.myspeedrunners.ui.feature.runnerdetails.model

import androidx.compose.ui.graphics.vector.ImageVector

data class RunnerDetailsTabItem(
    val tabType: RunnerDetailsTabType,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)