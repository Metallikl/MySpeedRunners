package com.dluche.myspeedrunners.data.datasource.game

import com.dluche.myspeedrunners.data.datasource.model.games.GameWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class GameDataSourceImpl @Inject constructor (
    private val client: HttpClient,
): GameDataSource {
    override suspend fun getRunnersGames(runnerId: String): GameWrapper? {
        return client.get("$RUNNER_GAMES_URL?$MODERATOR_PARAM=$runnerId").body()
    }

    companion object {
        private const val RUNNER_GAMES_URL = "games"
        private const val MODERATOR_PARAM = "moderator"
    }
}