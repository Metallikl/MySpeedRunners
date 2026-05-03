package com.dluche.myspeedrunners.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dluche.myspeedrunners.data.IoDispatcher
import com.dluche.myspeedrunners.data.datasource.run.RunDataSource
import com.dluche.myspeedrunners.data.mapper.asDomainModel
import com.dluche.myspeedrunners.data.paging.RunnerRunsPagingSource
import com.dluche.myspeedrunners.domain.QueryParams
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.run.PaginatedRun
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.repository.RunsRepository
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RunsRepositoryImpl @Inject constructor(
    private val dataSource: RunDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RunsRepository {
    override suspend fun getRunnerRuns(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): Result<PaginatedRun> {
        return withContext(dispatcher) {
            runCatching {
                dataSource.getRunnerRuns(
                    runnerId, embedParams,queryOrderBy
                ).asDomainModel()
            }
        }
    }

    override suspend fun searchRunnerRuns(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?,
        queryParams: QueryParams?
    ): Flow<PagingData<Run>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RunnerRunsPagingSource(
                    runDataSource = dataSource,
                    runnerId = runnerId,
                    embedParams = embedParams,
                    queryOrderBy = queryOrderBy,
                    queryParams = queryParams
                )
            }
        ).flow.flowOn(dispatcher)

    }

    override suspend fun getRuns(embedParams: EmbedParams?): Result<PaginatedRun> {
        return withContext(dispatcher) {
            runCatching {
                dataSource.getRuns(embedParams).asDomainModel()
            }
        }
    }

    override suspend fun getRunById(
        runId: String,
        embedParams: EmbedParams?
    ): Result<Run> {
        return withContext(dispatcher) {
            runCatching {
                dataSource.getRunById(
                    runId,embedParams
                ).data?.asDomainModel() ?: run{
                    Log.d("runId", "run is null")
                    throw Exception("RunnerNotFound")
                }
            }
        }
    }

    override suspend fun getGameRuns(
        gameId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): Result<PaginatedRun> {
        return withContext(dispatcher) {
            runCatching {
                dataSource.getGameRuns(
                    gameId, embedParams,queryOrderBy
                ).asDomainModel()
            }
        }
    }

    companion object{
        const val PAGE_SIZE = 20
    }
}