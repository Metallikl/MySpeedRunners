package com.dluche.myspeedrunners.data.datasource.run

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.data.datasource.model.run.RunWrapperDto

interface RunDataSource {

    suspend fun getRunnerRuns(runnerId: String,embedParams: EmbedParams?): RunWrapperDto

    suspend fun getRuns(embedParams: EmbedParams?): RunWrapperDto
}