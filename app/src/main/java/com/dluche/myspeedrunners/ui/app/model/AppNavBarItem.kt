package com.dluche.myspeedrunners.ui.app.model

import androidx.compose.ui.graphics.vector.ImageVector

data class AppNavBarItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String,
)
