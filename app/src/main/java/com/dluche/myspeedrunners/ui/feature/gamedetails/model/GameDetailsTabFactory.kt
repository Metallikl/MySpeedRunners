package com.dluche.myspeedrunners.ui.feature.gamedetails.model

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Leaderboard
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.RunCircle
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Leaderboard
import androidx.compose.material.icons.outlined.ManageAccounts
import androidx.compose.material.icons.outlined.RunCircle
import com.dluche.myspeedrunners.R

object GameDetailsTabFactory {
    fun getTabList(context: Context) = listOf(
        GameDetailTabItem(
            tabType = GameDetailTabType.LEADERBOARD,
            title = context.getString(R.string.leaderboard_tab_label),
            selectedIcon = Icons.Default.Leaderboard,
            unselectedIcon = Icons.Outlined.Leaderboard
        ),
        GameDetailTabItem(
            tabType = GameDetailTabType.RUNS,
            title = context.getString(R.string.runs_tab_label),
            selectedIcon = Icons.Filled.RunCircle,
            unselectedIcon = Icons.Outlined.RunCircle
        ),
//        GameDetailTabItem(
//            tabType = GameDetailTabType.RECORDS,
//            title = context.getString(R.string.records_tab_label),
//            selectedIcon = Icons.Filled.EmojiEvents,
//            unselectedIcon = Icons.Outlined.EmojiEvents
//        ),
        GameDetailTabItem(
            tabType = GameDetailTabType.MODERATORS,
            title = context.getString(R.string.moderator_tab_label),
            selectedIcon = Icons.Filled.ManageAccounts,
            unselectedIcon = Icons.Outlined.ManageAccounts
        ),
    )
}