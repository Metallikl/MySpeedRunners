package com.dluche.myspeedrunners.data.datasource

import com.dluche.myspeedrunners.data.datasource.model.RunnerDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import javax.inject.Inject

class RunnersDataSourceImpl @Inject constructor(
    private val client: HttpClient,

) : RunnersDataSource {
    override suspend fun getRunners(): List<RunnerDto>? {
        return listOf(RunnerDto())
    }

    override suspend fun getRunner(id: String): RunnerDto? {
        return client.get("/users/$id").body()
    }
}