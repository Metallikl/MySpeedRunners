package com.dluche.myspeedrunners.domain.usecase.runner

import com.dluche.myspeedrunners.domain.model.runner.RunnerCard

interface SearchRunnersUseCase {
   suspend operator fun invoke(name: String?): Result<List<RunnerCard>>
}