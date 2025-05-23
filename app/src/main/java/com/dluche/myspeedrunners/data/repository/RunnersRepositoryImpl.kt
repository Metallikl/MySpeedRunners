package com.dluche.myspeedrunners.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dluche.myspeedrunners.data.IoDispatcher
import com.dluche.myspeedrunners.data.datasource.runner.RunnersDataSource
import com.dluche.myspeedrunners.data.mapper.asDomainModel
import com.dluche.myspeedrunners.data.paging.RunnersPagingSource
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.domain.repository.RunnersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RunnersRepositoryImpl @Inject constructor(
    private val runnersDataSource: RunnersDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RunnersRepository {
    override suspend fun searchRunners(name: String?): Flow<PagingData<RunnerCard>> {
        return Pager(
            PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                RunnersPagingSource(
                    runnersDataSource = runnersDataSource,
                    query = name.orEmpty()
                )
            }
        ).flow.flowOn(dispatcher)
    }

    override suspend fun getRunner(id: String): Result<Runner> {
        return withContext(dispatcher) {
            runCatching {
                runnersDataSource.getRunner(id)?.wrapper?.asDomainModel() as Runner
            }
        }
    }

    companion object{
        const val PAGE_SIZE = 20
    }
}