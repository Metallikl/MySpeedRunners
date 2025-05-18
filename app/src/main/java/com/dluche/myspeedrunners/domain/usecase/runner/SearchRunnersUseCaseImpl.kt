package com.dluche.myspeedrunners.domain.usecase.runner

import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.domain.repository.RunnersRepository
import javax.inject.Inject

class SearchRunnersUseCaseImpl @Inject constructor(
    private val runnersRepository: RunnersRepository
) : SearchRunnersUseCase {
    override suspend fun invoke(name: String?): Result<List<RunnerCard>> {
        return runnersRepository.searchRunners(name)
    }
}