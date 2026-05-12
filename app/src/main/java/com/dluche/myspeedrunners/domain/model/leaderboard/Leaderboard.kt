package com.dluche.myspeedrunners.domain.model.leaderboard

import com.dluche.myspeedrunners.domain.model.category.Category
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.platform.Platform

data class Leaderboard(
    val weblink: String,
    val game: Game,
    val category: Category,
    val timing: String,
    val runs: List<LeaderboardRun>,
    val platforms: List<Platform>
)
