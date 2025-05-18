package com.dluche.myspeedrunners.data.repository

import com.dluche.myspeedrunners.data.IoDispatcher
import com.dluche.myspeedrunners.data.datasource.runner.RunnersDataSource
import com.dluche.myspeedrunners.data.mapper.asCardDomainModel
import com.dluche.myspeedrunners.data.mapper.asDomainModel
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.domain.repository.RunnersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RunnersRepositoryImpl @Inject constructor(
    private val runnersDataSource: RunnersDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RunnersRepository {
    override suspend fun searchRunners(name: String?): Result<List<RunnerCard>> {
        return withContext(dispatcher) {
            runCatching {
                val runnersList = mutableListOf<RunnerCard>()
                runnersDataSource.searchRunners(name)?.wrapper?.map { runnerWrapper->
                    runnerWrapper.let{
                        runnersList.add(it.asCardDomainModel())
                    }
                }
                runnersList
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