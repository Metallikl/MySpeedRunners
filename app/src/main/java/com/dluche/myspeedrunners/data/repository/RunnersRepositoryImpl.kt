package com.dluche.myspeedrunners.data.repository

import com.dluche.myspeedrunners.data.IoDispatcher
import com.dluche.myspeedrunners.data.datasource.RunnersDataSource
import com.dluche.myspeedrunners.data.datasource.model.RunnerDto
import com.dluche.myspeedrunners.domain.repository.RunnersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RunnersRepositoryImpl @Inject constructor(
    private val runnersDataSource: RunnersDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RunnersRepository {
    override suspend fun getRunners(): Result<List<RunnerDto>> {
        return withContext(dispatcher) {
            runCatching {
                runnersDataSource.getRunners() as List<RunnerDto>
            }
        }
    }

    override suspend fun getRunner(id: String): Result<RunnerDto> {
        return withContext(dispatcher) {
            runCatching {
                runnersDataSource.getRunner(id) as RunnerDto
            }
        }
    }
}