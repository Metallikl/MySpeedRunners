package com.dluche.myspeedrunners.domain.usecase.run

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.repository.RunsRepository
import javax.inject.Inject

class GetRunByIdUseCaseImpl @Inject constructor(
    private val repository: RunsRepository
) : GetRunByIdUseCase {
    override suspend fun invoke(
        runId: String,
        embedParams: EmbedParams?
    ): Result<Run> {
      return repository.getRunById(runId,embedParams)
    }
}