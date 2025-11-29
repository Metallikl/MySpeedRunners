package com.dluche.myspeedrunners.data.datasource.runner

import com.dluche.myspeedrunners.data.datasource.model.PaginatedRunnersWrapperDto
import com.dluche.myspeedrunners.data.datasource.model.RunnerWrapperDto
import com.dluche.myspeedrunners.data.datasource.model.personalbest.PersonalBestWrapperDto
import com.dluche.myspeedrunners.data.routes.ApiRoutes
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class RunnersDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : RunnersDataSource {
    override suspend fun searchRunners(name: String?, offset: Int?): PaginatedRunnersWrapperDto? {
        return client.get(ApiRoutes.Runners.getSearchRunners(name, offset)).body()
    }

    override suspend fun getRunner(id: String): RunnerWrapperDto? {
        return client.get(ApiRoutes.Runners.getRunnerById(id)).body()
    }

    override suspend fun getRunnerPersonalBests(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): PersonalBestWrapperDto? {
        return client.get(
            ApiRoutes.Runners.getRunnerPersonalBest(
                runnerId,
                embedParams,
                queryOrderBy
            )
        ).body()
    }
}