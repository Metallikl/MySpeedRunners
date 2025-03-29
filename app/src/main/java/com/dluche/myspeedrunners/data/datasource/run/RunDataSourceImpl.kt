package com.dluche.myspeedrunners.data.datasource.run

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.data.datasource.model.run.RunWrapperDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import javax.inject.Inject

class RunDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : RunDataSource {

    override suspend fun getRunnerRuns(
        runnerId: String,
        embedParams: EmbedParams?
    ): RunWrapperDto {
        val runParams = buildRunnerRunsUrl(runnerId, embedParams)
        return client.get("$RUNNER_RUNS_URL/$runParams").body()
    }

    private fun buildRunnerRunsUrl(
        runnerId: String,
        params: EmbedParams?
    ): String {
        val runnerInfo = if (runnerId.isNotBlank()) "?$USER_PARAM=$runnerId" else ""
        val embedInfo = buildEmbedInfo(params)
        return runnerInfo + embedInfo
    }

    override suspend fun getRuns(embedParams: EmbedParams?): RunWrapperDto {
        return client.get(RUNNER_RUNS_URL + buildEmbedInfo(embedParams)).body()
    }

    private fun buildEmbedInfo(params: EmbedParams?): String = params?.let {
        if (params.param1.isNotBlank() && params.param2.isNotBlank()) {
            "&$EMBED_PARAM=${params.param1},${params.param2}"
        } else {
            "&$EMBED_PARAM=${params.param1}${params.param2}"
        }
    }.orEmpty()

    companion object {
        private const val RUNNER_RUNS_URL = "runs"
        private const val USER_PARAM = "user"
        private const val EMBED_PARAM = "embed"
    }
}