package com.dluche.myspeedrunners.domain.repository

import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard

interface RunnersRepository {

    suspend fun searchRunners(name: String? = null): Result<List<RunnerCard>>

    suspend fun getRunner(id: String): Result<Runner>
}