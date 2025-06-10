package com.dluche.myspeedrunners.domain.usecase.game

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.repository.GamesRepository
import javax.inject.Inject

class GetGameDetailsUseCaseImpl @Inject constructor (
    private val gamesRepository: GamesRepository
): GetGameDetailsUseCase {
    override suspend fun invoke(
        gameId: String,
        params: EmbedParams
    ): Result<Game> {
        return gamesRepository.getGameDetails(gameId,params)
    }
}