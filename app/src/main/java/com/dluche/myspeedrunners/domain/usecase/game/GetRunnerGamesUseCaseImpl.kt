package com.dluche.myspeedrunners.domain.usecase.game

import com.dluche.myspeedrunners.domain.repository.GamesRepository
import javax.inject.Inject

class GetRunnerGamesUseCaseImpl @Inject constructor(
    private val gamesRepository: GamesRepository
) : GetRunnerGamesUseCase {
    override suspend fun invoke(runnerId: String) = gamesRepository.getRunnersGames(runnerId)
}