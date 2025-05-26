package com.dluche.myspeedrunners.domain.usecase.runner

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.domain.repository.RunnersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRunnersUseCaseImpl @Inject constructor(
    private val runnersRepository: RunnersRepository
) : SearchRunnersUseCase {
    override suspend fun invoke(name: String?): Flow<PagingData<RunnerCard>> {
        return runnersRepository.searchRunners(name)
    }
}