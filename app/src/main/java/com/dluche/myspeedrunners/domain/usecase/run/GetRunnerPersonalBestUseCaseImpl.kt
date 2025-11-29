package com.dluche.myspeedrunners.domain.usecase.run

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.repository.RunnersTempRepository
import javax.inject.Inject

class GetRunnerPersonalBestUseCaseImpl @Inject constructor(
    val repository: RunnersTempRepository
) : GetRunnerPersonalBestUseCase {
    override suspend fun invoke(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?,
        gameIdFilter: String?
    ): Result<List<Run>> {
        return repository.getRunnerPersonalBest(runnerId, embedParams, queryOrderBy, gameIdFilter)
    }
}