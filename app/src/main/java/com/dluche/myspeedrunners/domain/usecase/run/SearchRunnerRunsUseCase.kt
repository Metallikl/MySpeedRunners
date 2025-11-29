package com.dluche.myspeedrunners.domain.usecase.run

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.QueryParams
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.run.Run
import kotlinx.coroutines.flow.Flow

interface SearchRunnerRunsUseCase {
    suspend operator fun invoke(
        runnerId: String,
        embedParams: EmbedParams? = null,
        queryOrderBy: QueryOrderBy? = null,
        queryParams: QueryParams? = null
    ): Flow<PagingData<Run>>
}
