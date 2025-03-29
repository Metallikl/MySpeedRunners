package com.dluche.myspeedrunners.data.repository

import com.dluche.myspeedrunners.data.IoDispatcher
import com.dluche.myspeedrunners.data.datasource.RunnersDataSource
import com.dluche.myspeedrunners.data.datasource.model.RunnerWrapperDto
import com.dluche.myspeedrunners.data.mapper.asDomainModel
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.repository.RunnersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RunnersRepositoryImpl @Inject constructor(
    private val runnersDataSource: RunnersDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RunnersRepository {
    override suspend fun getRunners(): Result<List<RunnerWrapperDto>> {
        return withContext(dispatcher) {
            runCatching {
                runnersDataSource.getRunners() as List<RunnerWrapperDto>
            }
        }
    }

    override suspend fun getRunner(id: String): Result<Runner> {
        return withContext(dispatcher) {
            runCatching {
                runnersDataSource.getRunner(id)?.wrapper?.asDomainModel() as Runner
            }
        }
    }
}