package com.dluche.myspeedrunners.domain.usecase

import com.dluche.myspeedrunners.domain.model.runner.Runner

interface GetRunnerUseCase {
    suspend operator fun invoke(id: String): Result<Runner>
}