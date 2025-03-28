package com.dluche.myspeedrunners.domain.repository

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.data.datasource.model.run.RunWrapperDto

interface RunsRepository {

    suspend fun getRunnerRuns(runnerId: String,embedParams: EmbedParams?): RunWrapperDto

    suspend fun getRuns(embedParams: EmbedParams?): RunWrapperDto
}