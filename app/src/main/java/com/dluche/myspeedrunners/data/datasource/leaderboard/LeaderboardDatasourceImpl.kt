package com.dluche.myspeedrunners.data.datasource.leaderboard

import com.dluche.myspeedrunners.data.datasource.model.leaderboard.LeaderboardDtoWrapper
import com.dluche.myspeedrunners.data.routes.ApiRoutes
import com.dluche.myspeedrunners.data.util.buildEmbedInfo
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class LeaderboardDatasourceImpl @Inject constructor(
    private val client: HttpClient,
) : LeaderboardDatasource {
    override suspend fun fetchDefaultLeaderboard(url: String, embedParams: EmbedParams?): LeaderboardDtoWrapper {
        val newUrl = getLeaderboardUrl(url)+embedParams.buildEmbedInfo(true)
        return client.get(newUrl).body()
    }

    private fun getLeaderboardUrl(url: String): String {
        return try {
            url.split("v1/").let {
                it[it.lastIndex]
            }
        } catch (e: Exception) {
            url
        }
    }

    override suspend fun filterLeaderboard(
        gameId: String,
        categoryId: String,
        platformId: String?,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): LeaderboardDtoWrapper {
        return client.get(
            ApiRoutes.Leaderboards.getLeaderboards(
                gameId = gameId,
                categoryId = categoryId,
                platformId = platformId,
                embedParams = embedParams,
                queryOrderBy = queryOrderBy
            )
        ).body()
    }
}