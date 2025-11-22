package com.dluche.myspeedrunners.domain.usecase.run

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.run.Run

interface GetRunnerPersonalBestUseCase {
    suspend operator fun invoke(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?,
        gameIdFilter: String? = null
    ): Result<List<Run>>
}