package com.dluche.myspeedrunners.domain.usecase.run

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.run.Run

interface GetRunByIdUseCase {
    suspend operator fun invoke(
        runId: String,
        embedParams: EmbedParams? = null
    ): Result<Run>
}