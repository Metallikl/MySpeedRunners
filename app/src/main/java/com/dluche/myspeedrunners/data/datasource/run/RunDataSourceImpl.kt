package com.dluche.myspeedrunners.data.datasource.run

import com.dluche.myspeedrunners.data.datasource.model.run.RunSingleWrapperDto
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.data.datasource.model.run.RunWrapperDto
import com.dluche.myspeedrunners.data.util.buildEmbedInfo
import com.dluche.myspeedrunners.data.util.buildOffsetInfo
import com.dluche.myspeedrunners.data.util.buildOrderByInfo
import com.dluche.myspeedrunners.data.util.buildQueryParamsInfo
import com.dluche.myspeedrunners.domain.QueryParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

import javax.inject.Inject

class RunDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : RunDataSource {

    override suspend fun getRunnerRuns(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): RunWrapperDto {
        val runParams = buildRunnerRunsUrl(
            runnerId,
            embedParams,
            queryOrderBy
        )
        return client.get("$RUNNER_RUNS_URL$runParams").body()
    }

    private fun buildRunnerRunsUrl(
        runnerId: String,
        params: EmbedParams?,
        queryOrderBy: QueryOrderBy?,
        offset: Int? = null,
        queryParams: QueryParams? = null
    ): String {
//        val runnerInfo = if (runnerId.isNotBlank()) "?$USER_PARAM=$runnerId" else ""
//        val embedInfo = params.buildEmbedInfo(runnerInfo.isBlank())
//        val orderBy = queryOrderBy.buildOrderByInfo()
//        val offsetInfo = buildOffsetInfo(offset)
//        val queryParams = queryParams.buildQueryParamsInfo(embedInfo.isEmpty())
//
//        return runnerInfo + embedInfo + orderBy + offsetInfo

        return StringBuilder().apply {
            append(if (runnerId.isNotBlank()) "?$USER_PARAM=$runnerId" else "")
            append(params.buildEmbedInfo(this.isEmpty()))
            append(queryOrderBy.buildOrderByInfo())
            append(queryParams.buildQueryParamsInfo(this.isEmpty()))
            append(buildOffsetInfo(offset))
        }.toString()
    }

    override suspend fun searchRunnerRuns(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?,
        offset: Int?,
        queryParams: QueryParams?,
    ): RunWrapperDto {
        val runParams = buildRunnerRunsUrl(runnerId, embedParams, queryOrderBy,offset,queryParams)
        return client.get("$RUNNER_RUNS_URL$runParams").body()
    }

    override suspend fun getRuns(embedParams: EmbedParams?): RunWrapperDto {
        return client.get(RUNNER_RUNS_URL + embedParams.buildEmbedInfo(true)).body()
    }


    override suspend fun getRunById(
        runId: String,
        embedParams: EmbedParams?
    ): RunSingleWrapperDto {
        return client.get(RUNNER_RUNS_URL +"/" + runId  + embedParams.buildEmbedInfo(true)).body()
    }

    companion object {
        private const val RUNNER_RUNS_URL = "runs"
        private const val USER_PARAM = "user"
    }
}