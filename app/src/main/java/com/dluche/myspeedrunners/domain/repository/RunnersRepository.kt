package com.dluche.myspeedrunners.domain.repository

import com.dluche.myspeedrunners.data.datasource.model.RunnerDto

interface RunnersRepository {

    suspend fun getRunners(): Result<List<RunnerDto>>

    suspend fun getRunner(id: String): Result<RunnerDto>
}