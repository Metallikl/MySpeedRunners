package com.dluche.myspeedrunners.domain.usecase.game

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.repository.RunnersTempRepository
import javax.inject.Inject

class GetGamesFromPersonalBestUseCaseImpl @Inject constructor(
    val repository: RunnersTempRepository
) : GetGamesFromPersonalBestUseCase {
    override suspend fun invoke(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ) = repository.getRunnerPersonalBestAsGameFilter(runnerId, embedParams, queryOrderBy)
}