package com.dluche.myspeedrunners.data.repository

import com.dluche.myspeedrunners.data.IoDispatcher
import com.dluche.myspeedrunners.data.datasource.game.GameDataSource
import com.dluche.myspeedrunners.data.mapper.asDomainModel
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.repository.GamesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val gameDataSource: GameDataSource,
    @IoDispatcher private val dispatcherIo: CoroutineDispatcher
) : GamesRepository {
    override suspend fun getRunnersGames(runnerId: String): Result<List<Game>> {
        return withContext(dispatcherIo) {
            runCatching {
                gameDataSource
                    .getRunnersGames(runnerId)
                    ?.data
                    ?.map { it.asDomainModel() }
                    .orEmpty()
            }
        }
    }

    override suspend fun getGameDetails(
        gameId: String,
        params: EmbedParams
    ): Result<Game> {
        return withContext(dispatcherIo) {
            runCatching {
                gameDataSource
                    .getGameDetails(gameId,params).asDomainModel()
            }
        }
    }
}