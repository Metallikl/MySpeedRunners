package com.dluche.myspeedrunners.data.datasource.runner

import com.dluche.myspeedrunners.data.datasource.model.PaginatedRunnersWrapperDto
import com.dluche.myspeedrunners.data.datasource.model.RunnerWrapperDto
import com.dluche.myspeedrunners.data.routes.ApiRoutes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class RunnersDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : RunnersDataSource {
    override suspend fun searchRunners(name: String?, offset: Int?): PaginatedRunnersWrapperDto? {
        return client.get(ApiRoutes.Runners.getSearchRunners(name,offset)).body()
    }

    override suspend fun getRunner(id: String): RunnerWrapperDto? {
        return client.get(ApiRoutes.Runners.getRunnerById(id)).body()
    }
}