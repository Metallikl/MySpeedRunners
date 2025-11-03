package com.dluche.myspeedrunners.domain.usecase.runner

import com.dluche.myspeedrunners.domain.model.runner.RunnerCard

interface GetRunnerCardUseCase {
    suspend operator fun invoke(id: String): Result<RunnerCard>
}