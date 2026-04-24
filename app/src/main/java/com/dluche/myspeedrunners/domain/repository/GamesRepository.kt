package com.dluche.myspeedrunners.domain.repository

import androidx.paging.PagingData
import com.dluche.myspeedrunners.data.datasource.model.games.GameDetailsDto
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.game.GameCard
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun getRunnersGames(runnerId: String): Result<List<Game>>

    suspend fun getGameDetails(gameId: String,params: EmbedParams): Result<Game>

    suspend fun searchGames(name: String?): Flow<PagingData<GameCard>>
}