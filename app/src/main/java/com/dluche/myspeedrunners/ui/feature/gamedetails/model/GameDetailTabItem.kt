package com.dluche.myspeedrunners.ui.feature.gamedetails.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.model.RunnerRunsTabType

data class GameDetailTabItem(
    val tabType: GameDetailTabType,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
