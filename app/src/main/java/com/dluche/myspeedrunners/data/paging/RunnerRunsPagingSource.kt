package com.dluche.myspeedrunners.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dluche.myspeedrunners.data.datasource.run.RunDataSource
import com.dluche.myspeedrunners.data.mapper.asDomainModel
import com.dluche.myspeedrunners.domain.QueryParams
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.run.Run
import javax.inject.Inject

class RunnerRunsPagingSource @Inject constructor(
    private val runDataSource: RunDataSource,
    private val runnerId: String,
    private val embedParams: EmbedParams?,
    private val queryOrderBy: QueryOrderBy?,
    private val queryParams: QueryParams?
) : PagingSource<Int, Run>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Run> {
        return try {

            val offset: Int = params.key ?: 0

            val data = runDataSource.searchRunnerRuns(
                runnerId = runnerId,
                embedParams = embedParams,
                queryOrderBy = queryOrderBy,
                offset = offset,
                queryParams = queryParams
            )

            val nextKey = data.pagination?.let { pagination ->
                if (pagination.offset != null
                    && pagination.max != null
                    && pagination.size != null
                    && pagination.size >= pagination.max
                ) {
                    pagination.offset + pagination.max
                } else null
            }
            val runnersList = data.data.map {
                it.asDomainModel()
            }.orEmpty()

            LoadResult.Page(
                data = runnersList,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Run>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize)
                ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }
}