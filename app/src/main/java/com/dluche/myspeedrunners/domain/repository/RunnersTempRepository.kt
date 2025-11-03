package com.dluche.myspeedrunners.domain.repository

import com.dluche.myspeedrunners.domain.model.runner.RunnerCard

interface RunnersTempRepository {

    suspend fun getRunnerCard(id: String): Result<RunnerCard>

    suspend fun saveRunnerCard(runnerCard: RunnerCard): Boolean

    suspend fun deleteRunnerCard(runnerCard: RunnerCard)
}