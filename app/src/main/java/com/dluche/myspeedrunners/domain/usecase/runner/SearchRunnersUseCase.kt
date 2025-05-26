package com.dluche.myspeedrunners.domain.usecase.runner

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import kotlinx.coroutines.flow.Flow

interface SearchRunnersUseCase {
   suspend operator fun invoke(name: String?): Flow<PagingData<RunnerCard>>
}