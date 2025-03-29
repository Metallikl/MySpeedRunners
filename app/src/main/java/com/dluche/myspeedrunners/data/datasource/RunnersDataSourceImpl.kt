package com.dluche.myspeedrunners.data.datasource

import com.dluche.myspeedrunners.data.datasource.model.RunnerWrapperDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import javax.inject.Inject
import io.ktor.client.request.get

class RunnersDataSourceImpl @Inject constructor(
    private val client: HttpClient,

) : RunnersDataSource {
    override suspend fun getRunners(): List<RunnerWrapperDto>? {
        return listOf(RunnerWrapperDto())
    }

    override suspend fun getRunner(id: String): RunnerWrapperDto? {
        return client.get("users/$id").body()
    }
}