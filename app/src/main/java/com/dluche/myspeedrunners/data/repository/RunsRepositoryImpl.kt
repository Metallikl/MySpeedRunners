package com.dluche.myspeedrunners.data.repository

import com.dluche.myspeedrunners.data.IoDispatcher
import com.dluche.myspeedrunners.data.datasource.run.RunDataSource
import com.dluche.myspeedrunners.data.mapper.asDomainModel
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.run.PaginatedRun
import com.dluche.myspeedrunners.domain.repository.RunsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RunsRepositoryImpl @Inject constructor(
    private val dataSource: RunDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RunsRepository {
    override suspend fun getRunnerRuns(
        runnerId: String,
        embedParams: EmbedParams?
    ): Result<PaginatedRun> {
        return withContext(dispatcher) {
            runCatching {
                dataSource.getRunnerRuns(
                    runnerId, embedParams
                ).asDomainModel()
            }
        }
    }

    override suspend fun getRuns(embedParams: EmbedParams?): Result<PaginatedRun> {
        return withContext(dispatcher) {
            runCatching {
                dataSource.getRuns(embedParams).asDomainModel()
            }
        }
    }
}