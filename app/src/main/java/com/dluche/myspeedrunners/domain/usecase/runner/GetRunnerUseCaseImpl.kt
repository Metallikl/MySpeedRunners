package com.dluche.myspeedrunners.domain.usecase.runner

import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.repository.RunnersRepository
import javax.inject.Inject

class GetRunnerUseCaseImpl @Inject constructor(
    private val runnersRepository: RunnersRepository
): GetRunnerUseCase {
    override suspend fun invoke(id: String): Result<Runner> {
        return runnersRepository.getRunner(id)
    }
}