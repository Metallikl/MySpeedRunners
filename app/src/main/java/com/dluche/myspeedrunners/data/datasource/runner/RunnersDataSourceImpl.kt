package com.dluche.myspeedrunners.data.datasource.runner

import com.dluche.myspeedrunners.data.datasource.model.RunnerWrapperDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

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