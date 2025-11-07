package com.dluche.myspeedrunners.domain.repository

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import kotlinx.coroutines.flow.Flow

interface RunnersRepository {

    suspend fun searchRunners(name: String? = null): Flow<PagingData<RunnerCard>>

    suspend fun getRunner(id: String): Result<Runner>

    suspend fun getRunnerCard(id: String): Result<RunnerCard>

    suspend fun getRunnerPersonalBest(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): Result<List<Run>>

    suspend fun getRunnerPersonalBestAsGameFilter(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): Result<List<Game>>


}