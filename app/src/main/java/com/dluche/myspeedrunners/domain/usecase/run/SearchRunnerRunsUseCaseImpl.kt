package com.dluche.myspeedrunners.domain.usecase.run

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.QueryParams
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.repository.RunsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRunnerRunsUseCaseImpl @Inject constructor(
    private val repository: RunsRepository
): SearchRunnerRunsUseCase {

    override suspend fun invoke(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?,
        queryParams: QueryParams?
    ): Flow<PagingData<Run>> {
        return repository.searchRunnerRuns(runnerId, embedParams, queryOrderBy,queryParams)
    }
}