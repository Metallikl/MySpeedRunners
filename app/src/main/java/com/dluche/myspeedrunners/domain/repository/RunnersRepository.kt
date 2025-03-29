package com.dluche.myspeedrunners.domain.repository

import com.dluche.myspeedrunners.data.datasource.model.RunnerWrapperDto
import com.dluche.myspeedrunners.domain.model.runner.Runner

interface RunnersRepository {

    suspend fun getRunners(): Result<List<RunnerWrapperDto>>

    suspend fun getRunner(id: String): Result<Runner>
}