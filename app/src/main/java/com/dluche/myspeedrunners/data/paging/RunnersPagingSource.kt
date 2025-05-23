package com.dluche.myspeedrunners.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dluche.myspeedrunners.data.datasource.runner.RunnersDataSource
import com.dluche.myspeedrunners.data.mapper.asCardDomainModel
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import javax.inject.Inject

class RunnersPagingSource @Inject constructor(
    private val runnersDataSource: RunnersDataSource,
    private val query: String
) : PagingSource<Int, RunnerCard>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RunnerCard> {
        return try {
            val offset: Int = params.key ?: 20

            val data = runnersDataSource.searchRunners(query, offset)
            val nextKey = data?.pagination?.let { pagination ->
                if (pagination.offset != null
                    && pagination.max != null
                    && pagination.size != null
                    && pagination.size >= pagination.max
                ) {
                    pagination.offset + pagination.max
                } else null
            }
            val runnersList = data?.wrapper?.map {
                it.asCardDomainModel()
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

    override fun getRefreshKey(state: PagingState<Int, RunnerCard>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize)
                ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }
}