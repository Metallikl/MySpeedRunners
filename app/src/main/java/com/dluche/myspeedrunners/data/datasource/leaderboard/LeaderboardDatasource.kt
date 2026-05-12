package com.dluche.myspeedrunners.data.datasource.leaderboard

import com.dluche.myspeedrunners.data.datasource.model.leaderboard.LeaderboardDtoWrapper
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy

interface LeaderboardDatasource {

    suspend fun fetchDefaultLeaderboard(url: String,embedParams: EmbedParams?): LeaderboardDtoWrapper

    suspend fun filterLeaderboard(
        gameId: String,
        categoryId: String,
        platformId: String?,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): LeaderboardDtoWrapper
}