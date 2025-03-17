package com.dluche.myspeedrunners.data.datasource

import com.dluche.myspeedrunners.data.datasource.model.RunnerDto

internal interface RunnerDataSource {

    suspend fun getRunners(): List<RunnerDto>?

    suspend fun getRunners(id: String): RunnerDto?
}