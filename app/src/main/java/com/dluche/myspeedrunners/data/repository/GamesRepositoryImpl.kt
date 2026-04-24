package com.dluche.myspeedrunners.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dluche.myspeedrunners.data.IoDispatcher
import com.dluche.myspeedrunners.data.datasource.game.GameDataSource
import com.dluche.myspeedrunners.data.mapper.asDomainModel
import com.dluche.myspeedrunners.data.paging.GamePagingSource
import com.dluche.myspeedrunners.data.paging.RunnersPagingSource
import com.dluche.myspeedrunners.data.repository.RunnersRepositoryImpl.Companion.PAGE_SIZE
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.game.GameCard
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.domain.repository.GamesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
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
                    .getGameDetails(gameId,params)?.data.asDomainModel()
            }
        }
    }

    override suspend fun searchGames(name: String?): Flow<PagingData<GameCard>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GamePagingSource(
                    gameDataSource = gameDataSource,
                    query = name.orEmpty()
                )
            }
        ).flow.flowOn(dispatcherIo)
    }
}