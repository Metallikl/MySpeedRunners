package com.dluche.myspeedrunners.domain.repository

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.run.PaginatedRun

interface RunsRepository {

    suspend fun getRunnerRuns(runnerId: String,embedParams: EmbedParams?, queryOrderBy: QueryOrderBy?): Result<PaginatedRun>

    suspend fun getRuns(embedParams: EmbedParams?): Result<PaginatedRun>
}