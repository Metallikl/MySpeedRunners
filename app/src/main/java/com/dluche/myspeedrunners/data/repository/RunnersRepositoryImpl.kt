package com.dluche.myspeedrunners.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dluche.myspeedrunners.data.IoDispatcher
import com.dluche.myspeedrunners.data.datasource.runner.RunnersDataSource
import com.dluche.myspeedrunners.data.mapper.asCardDomainModel
import com.dluche.myspeedrunners.data.mapper.asDomainModel
import com.dluche.myspeedrunners.data.paging.RunnersPagingSource
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.domain.repository.RunnersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RunnersRepositoryImpl @Inject constructor(
    private val runnersDataSource: RunnersDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RunnersRepository {
    override suspend fun searchRunners(name: String?): Flow<PagingData<RunnerCard>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RunnersPagingSource(
                    runnersDataSource = runnersDataSource,
                    query = name.orEmpty()
                )
            }
        ).flow.flowOn(dispatcher)
    }

    override suspend fun getRunner(id: String): Result<Runner> {
        return withContext(dispatcher) {
            runCatching {
                runnersDataSource.getRunner(id)?.wrapper?.asDomainModel() as Runner
            }
        }
    }

    override suspend fun getRunnerCard(id: String): Result<RunnerCard> {
        return withContext(dispatcher) {
            runCatching {
                runnersDataSource.getRunner(id)?.wrapper?.asCardDomainModel() as RunnerCard
            }
        }
    }

    override suspend fun getRunnerPersonalBest(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): Result<List<Run>> {
        return withContext(dispatcher) {
            runCatching {
                runnersDataSource.getRunnerPersonalBests(
                    runnerId = runnerId,
                    embedParams = embedParams,
                    queryOrderBy = queryOrderBy
                )?.data?.filter { pbDto ->
                    pbDto.run != null
                }?.map {
                    it.run!!.asDomainModel()
                } ?: emptyList()
            }
        }
    }

    override suspend fun getRunnerPersonalBestAsGameFilter(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): Result<List<Game>> {
        return withContext(dispatcher) {
            runCatching {
                runnersDataSource.getRunnerPersonalBests(
                    runnerId = runnerId,
                    embedParams = embedParams,
                    queryOrderBy = queryOrderBy
                )?.data?.filter{
                    it.game != null
                }?.distinctBy {
                    it.game?.data?.id
                }?.map {
                    it.game?.data.asDomainModel()
                } ?: emptyList()
            }
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}