package com.dluche.myspeedrunners.data.repository

import com.dluche.myspeedrunners.data.IoDispatcher
import com.dluche.myspeedrunners.data.datasource.model.category.CategoryDtoType
import com.dluche.myspeedrunners.data.datasource.model.games.GameDtoType
import com.dluche.myspeedrunners.data.datasource.model.personalbest.PersonalBestDto
import com.dluche.myspeedrunners.data.datasource.model.run.RunDto
import com.dluche.myspeedrunners.data.datasource.runner.RunnersDataSource
import com.dluche.myspeedrunners.data.mapper.asCardDomainModel
import com.dluche.myspeedrunners.data.mapper.asDomainModel
import com.dluche.myspeedrunners.data.mapper.handlePlayerDtoType
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.game.GameCard
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.domain.repository.RunnersTempRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RunnersTempRepositoryImpl @Inject constructor(
    private val runnersDataSource: RunnersDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RunnersTempRepository {
    private val runnerCards = hashMapOf<String, RunnerCard>()
    private var rawPersonalBest: List<PersonalBestDto>? = null

    override suspend fun getRunnerCard(id: String): Result<RunnerCard> {
        return runCatching {
            runnerCards[id] ?: throw Exception("Runner card not found")
        }
    }

    override suspend fun saveRunnerCard(runnerCard: RunnerCard): Boolean {
        return try {
            runnerCards[runnerCard.id] = runnerCard
            true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun deleteRunnerCard(runnerCard: RunnerCard) {
        runnerCards.remove(runnerCard.id)
    }

    override suspend fun getRunnerPersonalBest(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?,
        gameIdFilter: String?
    ): Result<List<Run>> {
        return withContext(dispatcher) {
            runCatching {
                getPersonalBest(
                    runnerId = runnerId,
                    embedParams = embedParams,
                    queryOrderBy = queryOrderBy
                )?.filter { pbDto ->
                    pbDto.run != null && (gameIdFilter == null || pbDto.game?.data?.id == gameIdFilter)
                }?.map { pbDto ->
                    pbDto.run?.addCategoryAndGame(pbDto)!!.asDomainModel()
                } ?: emptyList()
            }
        }
    }

    private fun RunDto.addCategoryAndGame(pbDto: PersonalBestDto) = this.copy(
        gameEmbedDto = pbDto.game?.let { gameDto ->
            GameDtoType.GameEmbed(gameDto)
        },
        categoryEmbed = pbDto.category?.let { categoryDto ->
            CategoryDtoType.CategoryEmbed(categoryDto)
        }
    )

    override suspend fun getRunnerPersonalBestAsGameFilter(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): Result<List<GameCard>> {
        return withContext(dispatcher) {
            runCatching {
                getPersonalBest(
                    runnerId = runnerId,
                    embedParams = embedParams,
                    queryOrderBy = queryOrderBy
                )?.filter {
                    it.game != null
                }?.distinctBy {
                    it.game?.data?.id
                }?.map {
                    it.game?.data.asCardDomainModel()
                } ?: emptyList()
            }
        }
    }

    private suspend fun getPersonalBest(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): List<PersonalBestDto>? {
        return if (rawPersonalBest.isNullOrEmpty() || runnerIdHasChange(runnerId)) {
            runnersDataSource.getRunnerPersonalBests(
                runnerId = runnerId,
                embedParams = embedParams,
                queryOrderBy = queryOrderBy
            )?.data.also {
                rawPersonalBest = it
            }
        } else rawPersonalBest
    }

    private fun runnerIdHasChange(
        runnerId: String
    ) = rawPersonalBest?.first()?.run?.players?.handlePlayerDtoType()?.find { it.id == runnerId } == null
}