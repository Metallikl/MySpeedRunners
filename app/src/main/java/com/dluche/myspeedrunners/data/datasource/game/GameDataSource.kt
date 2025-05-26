package com.dluche.myspeedrunners.data.datasource.game

import com.dluche.myspeedrunners.data.datasource.model.games.GameWrapper

interface GameDataSource {

    suspend fun getRunnersGames(runnerId: String): GameWrapper?

}