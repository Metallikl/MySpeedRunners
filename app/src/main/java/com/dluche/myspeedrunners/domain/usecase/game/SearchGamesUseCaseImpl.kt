package com.dluche.myspeedrunners.domain.usecase.game

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.model.game.GameCard
import com.dluche.myspeedrunners.domain.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchGamesUseCaseImpl @Inject constructor(
    private val gamesRepository: GamesRepository
) : SearchGamesUseCase {
    override suspend fun invoke(name: String?): Flow<PagingData<GameCard>> {
        return gamesRepository.searchGames(name)
    }
}