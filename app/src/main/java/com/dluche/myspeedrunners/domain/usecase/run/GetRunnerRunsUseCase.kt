package com.dluche.myspeedrunners.domain.usecase.run

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.run.PaginatedRun

interface GetRunnerRunsUseCase {
    suspend operator fun invoke(
        runnerId: String,
        embedParams: EmbedParams? = null,
        queryOrderBy: QueryOrderBy? = null
    ): Result<PaginatedRun>
}