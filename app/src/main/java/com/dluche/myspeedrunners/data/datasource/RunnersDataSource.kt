package com.dluche.myspeedrunners.data.datasource

import com.dluche.myspeedrunners.data.datasource.model.RunnerDto

interface RunnersDataSource {

    suspend fun getRunners(): List<RunnerDto>?

    suspend fun getRunner(id: String): RunnerDto?
}