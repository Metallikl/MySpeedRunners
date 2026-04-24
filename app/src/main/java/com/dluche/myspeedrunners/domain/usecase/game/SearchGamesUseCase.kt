package com.dluche.myspeedrunners.domain.usecase.game

import androidx.paging.PagingData
import com.dluche.myspeedrunners.domain.model.game.GameCard
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import kotlinx.coroutines.flow.Flow

interface SearchGamesUseCase {
   suspend operator fun invoke(name: String?): Flow<PagingData<GameCard>>
}