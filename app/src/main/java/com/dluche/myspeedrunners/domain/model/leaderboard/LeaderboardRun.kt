package com.dluche.myspeedrunners.domain.model.leaderboard

import com.dluche.myspeedrunners.domain.model.category.Category
import com.dluche.myspeedrunners.domain.model.run.RunStatusEnum
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard

data class LeaderboardRun(
    val place: Int,
    val category: Category,
    val date: String,
    val id: String,
    val submitted: String,
    val status: RunStatusEnum,
    val primaryTime: String,
    val runner: RunnerCard
)
