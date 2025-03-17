package com.dluche.myspeedrunners.data.datasource

import com.dluche.myspeedrunners.data.datasource.model.RunnerDto
import io.ktor.client.HttpClient
import javax.inject.Inject

internal class RunnerDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : RunnerDataSource {
    override suspend fun getRunners(): List<RunnerDto>? {
        return listOf(RunnerDto())
    }

    override suspend fun getRunners(id: String): RunnerDto? {
        return RunnerDto()
    }
}