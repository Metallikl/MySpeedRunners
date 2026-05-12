package com.dluche.myspeedrunners.domain.repository

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.leaderboard.Leaderboard

interface LeaderboardRepository {
    suspend fun fetchDefaultLeaderboard(url: String,embedParams: EmbedParams?): Result<Leaderboard>

    suspend fun filterLeaderboard(
        gameId: String,
        categoryId: String,
        platformId: String?,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): Result<Leaderboard>
}