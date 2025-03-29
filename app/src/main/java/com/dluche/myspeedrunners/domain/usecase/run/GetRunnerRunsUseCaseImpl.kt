package com.dluche.myspeedrunners.domain.usecase.run

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.run.PaginatedRun
import com.dluche.myspeedrunners.domain.repository.RunsRepository
import javax.inject.Inject

class GetRunnerRunsUseCaseImpl @Inject constructor(
    private val runsRepository: RunsRepository
) : GetRunnerRunsUseCase {
    override suspend fun invoke(
        runnerId: String,
        embedParams: EmbedParams?
    ): Result<PaginatedRun> {
        return runsRepository.getRunnerRuns(runnerId, embedParams)
    }
}