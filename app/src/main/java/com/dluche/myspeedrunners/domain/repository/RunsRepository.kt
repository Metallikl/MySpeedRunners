package com.dluche.myspeedrunners.domain.repository

import androidx.paging.PagingData
import com.dluche.myspeedrunners.data.datasource.model.run.RunWrapperDto
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.run.PaginatedRun
import com.dluche.myspeedrunners.domain.model.run.Run
import kotlinx.coroutines.flow.Flow

interface RunsRepository {

    suspend fun getRunnerRuns(runnerId: String,embedParams: EmbedParams?, queryOrderBy: QueryOrderBy?): Result<PaginatedRun>

    suspend fun searchRunnerRuns(runnerId: String,embedParams: EmbedParams?, queryOrderBy: QueryOrderBy?): Flow<PagingData<Run>>

    suspend fun getRuns(embedParams: EmbedParams?): Result<PaginatedRun>

    suspend fun getRunById(runId: String,embedParams: EmbedParams?):  Result<Run>
}