package com.dluche.myspeedrunners.data.datasource.game

import com.dluche.myspeedrunners.data.datasource.model.games.GameDetailsDto
import com.dluche.myspeedrunners.data.datasource.model.games.GameWrapper
import com.dluche.myspeedrunners.domain.model.common.EmbedParams

interface GameDataSource {

    suspend fun getRunnersGames(runnerId: String): GameWrapper?

    suspend fun getGameDetails(gameId: String,params: EmbedParams): GameDetailsDto?

}