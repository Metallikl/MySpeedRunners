package com.dluche.myspeedrunners.domain.usecase.runner

import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.domain.repository.RunnersTempRepository
import javax.inject.Inject

class SaveRunnerCardUseCaseImpl @Inject constructor(
    private val runnerTempRepository: RunnersTempRepository
) : SaveRunnerCardUseCase {
    override suspend fun invoke(runnerCard: RunnerCard): Boolean {
        return runnerTempRepository.saveRunnerCard(runnerCard = runnerCard)
    }
}