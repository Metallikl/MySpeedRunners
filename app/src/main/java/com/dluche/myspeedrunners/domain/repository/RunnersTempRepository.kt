package com.dluche.myspeedrunners.domain.repository

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard

interface RunnersTempRepository {

    suspend fun getRunnerCard(id: String): Result<RunnerCard>

    suspend fun saveRunnerCard(runnerCard: RunnerCard): Boolean

    suspend fun deleteRunnerCard(runnerCard: RunnerCard)

    suspend fun getRunnerPersonalBest(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?,
        gameIdFilter: String?
    ): Result<List<Run>>

    suspend fun getRunnerPersonalBestAsGameFilter(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): Result<List<Game>>
}