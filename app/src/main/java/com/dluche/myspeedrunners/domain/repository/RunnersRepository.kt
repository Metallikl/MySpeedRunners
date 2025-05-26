package com.dluche.myspeedrunners.domain.repository

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import kotlinx.coroutines.flow.Flow

interface RunnersRepository {

    suspend fun searchRunners(name: String? = null): Flow<PagingData<RunnerCard>>

    suspend fun getRunner(id: String): Result<Runner>
}