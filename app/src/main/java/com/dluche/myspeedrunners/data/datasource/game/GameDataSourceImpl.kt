package com.dluche.myspeedrunners.data.datasource.game

import com.dluche.myspeedrunners.data.datasource.model.games.GameDetailsWrapper
import com.dluche.myspeedrunners.data.datasource.model.games.GameWrapper
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.EmbedParams.Companion.EMBED_PARAM
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

    override suspend fun getGameDetails(
        gameId: String,
        params: EmbedParams
    ): GameDetailsWrapper? {
        return client.get("$RUNNER_GAMES_URL/$gameId${buildEmbedInfo(params)}").body()
    }

    private fun buildEmbedInfo(params: EmbedParams?): String = params?.let {
        if (params.param1.isNotBlank() && params.param2.isNotBlank()  && params.param3.isNotBlank()) {
            "?$EMBED_PARAM=${params.param1},${params.param2},${params.param3}"
        } else {
            "?$EMBED_PARAM=${params.param1}${params.param2}"
        }
    }.orEmpty()

    companion object {
        private const val RUNNER_GAMES_URL = "games"
        private const val MODERATOR_PARAM = "moderator"
    }
}