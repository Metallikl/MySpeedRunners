package com.dluche.myspeedrunners.domain.usecase.runner

import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.domain.repository.RunnersRepository
import com.dluche.myspeedrunners.domain.repository.RunnersTempRepository
import javax.inject.Inject

class GetRunnerCardUseCaseImpl @Inject constructor(
    private val runnersRepository: RunnersRepository,
    private val runnersTempRepository: RunnersTempRepository
): GetRunnerCardUseCase {
    override suspend fun invoke(id: String): Result<RunnerCard> {
        return runnersTempRepository.getRunnerCard(id)
            .onSuccess {
                Result.success(it)
            }
            .onFailure {
                runnersRepository.getRunnerCard(id)
            }
    }
}