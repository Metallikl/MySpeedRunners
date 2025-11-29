package com.dluche.myspeedrunners.domain.usecase.runner

import com.dluche.myspeedrunners.domain.model.runner.RunnerCard

interface SaveRunnerCardUseCase {
    suspend operator fun invoke(runnerCard: RunnerCard): Boolean
}