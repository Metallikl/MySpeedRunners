package com.dluche.myspeedrunners.data.repository

import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.domain.repository.RunnersTempRepository
import javax.inject.Inject

class RunnersTempRepositoryImpl @Inject constructor() : RunnersTempRepository {
    val runnerCards = hashMapOf<String,RunnerCard>()

    override suspend fun getRunnerCard(id: String): Result<RunnerCard> {
        return  runCatching {
            runnerCards[id] ?: throw Exception("Runner card not found")
        }
    }

    override suspend fun saveRunnerCard(runnerCard: RunnerCard): Boolean {
        return try {
            runnerCards[runnerCard.id] = runnerCard
            true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun deleteRunnerCard(runnerCard: RunnerCard) {
        runnerCards.remove(runnerCard.id)
    }
}