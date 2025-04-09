package com.dluche.myspeedrunners.domain.repository

import com.dluche.myspeedrunners.domain.model.game.Game

interface GamesRepository {
    suspend fun getRunnersGames(runnerId: String): Result<List<Game>>
}