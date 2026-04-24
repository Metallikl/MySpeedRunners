package com.dluche.myspeedrunners.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dluche.myspeedrunners.data.datasource.game.GameDataSource
import com.dluche.myspeedrunners.data.mapper.asCardDomainModel
import com.dluche.myspeedrunners.domain.model.game.GameCard
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import javax.inject.Inject

class GamePagingSource @Inject constructor(
    private val gameDataSource: GameDataSource,
    private val query: String
) : PagingSource<Int, GameCard>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameCard> {
        return try {
            val offset: Int = params.key ?: 0

            val data = gameDataSource.searchGames(query, offset)
            val nextKey = data?.pagination?.let { pagination ->
                if (pagination.offset != null
                    && pagination.max != null
                    && pagination.size != null
                    && pagination.size >= pagination.max
                ) {
                    pagination.offset + pagination.max
                } else null
            }
            val runnersList = data?.data?.map {
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

    override fun getRefreshKey(state: PagingState<Int, GameCard>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(state.config.pageSize)
                ?: anchorPage?.nextKey?.minus(state.config.pageSize)
        }
    }
}