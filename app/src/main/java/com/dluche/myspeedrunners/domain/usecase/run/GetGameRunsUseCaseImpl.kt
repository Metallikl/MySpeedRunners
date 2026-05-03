package com.dluche.myspeedrunners.domain.usecase.run

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.run.PaginatedRun
import com.dluche.myspeedrunners.domain.repository.RunsRepository
import javax.inject.Inject

class GetGameRunsUseCaseImpl @Inject constructor(
    private val runsRepository: RunsRepository
) : GetGameRunsUseCase {
    override suspend fun invoke(
        gameId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): Result<PaginatedRun> {
        return runsRepository.getGameRuns(gameId, embedParams, queryOrderBy)
    }
}