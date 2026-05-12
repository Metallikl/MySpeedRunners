package com.dluche.myspeedrunners.data.repository

import com.dluche.myspeedrunners.data.IoDispatcher
import com.dluche.myspeedrunners.data.datasource.leaderboard.LeaderboardDatasource
import com.dluche.myspeedrunners.data.mapper.asDomainModel
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.leaderboard.Leaderboard
import com.dluche.myspeedrunners.domain.repository.LeaderboardRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LeaderboardRepositoryImpl @Inject constructor(
    private val leaderboardDatasource: LeaderboardDatasource,
    @IoDispatcher private val dispatcherIo: CoroutineDispatcher
) : LeaderboardRepository {

    override suspend fun fetchDefaultLeaderboard(url: String, embedParams: EmbedParams?): Result<Leaderboard> {
        return withContext(dispatcherIo) {
            runCatching {
                leaderboardDatasource
                    .fetchDefaultLeaderboard(url,embedParams)
                    .data.asDomainModel()

            }
        }
    }

    override suspend fun filterLeaderboard(
        gameId: String,
        categoryId: String,
        platformId: String?,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): Result<Leaderboard> {
        return withContext(dispatcherIo) {
            runCatching {
                leaderboardDatasource
                    .filterLeaderboard(
                        gameId,
                        categoryId,
                        platformId,
                        embedParams,
                        queryOrderBy
                    ).data.asDomainModel()
            }
        }
    }
}