package com.dluche.myspeedrunners.data.datasource.run

import com.dluche.myspeedrunners.data.datasource.model.run.RunSingleWrapperDto
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.data.datasource.model.run.RunWrapperDto
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy

interface RunDataSource {

    suspend fun getRunnerRuns(runnerId: String,embedParams: EmbedParams?, queryOrderBy: QueryOrderBy?): RunWrapperDto

    suspend fun getRuns(embedParams: EmbedParams?): RunWrapperDto

    suspend fun getRunById(runId: String,embedParams: EmbedParams?): RunSingleWrapperDto
}