package com.dluche.myspeedrunners.domain.repository

import com.dluche.myspeedrunners.data.datasource.model.games.GameDetailsDto
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.game.Game

interface GamesRepository {
    suspend fun getRunnersGames(runnerId: String): Result<List<Game>>

    suspend fun getGameDetails(gameId: String,params: EmbedParams): Result<Game>
}