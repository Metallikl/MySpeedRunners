package com.dluche.myspeedrunners.data.datasource.game

import com.dluche.myspeedrunners.data.datasource.model.games.GameDetailsWrapper
import com.dluche.myspeedrunners.data.datasource.model.games.GameWrapper
import com.dluche.myspeedrunners.data.util.buildEmbedInfo
import com.dluche.myspeedrunners.data.util.buildOrderByInfo
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class GameDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : GameDataSource {
    override suspend fun getRunnersGames(runnerId: String): GameWrapper? {
        return client.get("$RUNNER_GAMES_URL?$MODERATOR_PARAM=$runnerId").body()
    }

    override suspend fun getGameDetails(
        gameId: String,
        params: EmbedParams
    ): GameDetailsWrapper? {
        return client.get("$RUNNER_GAMES_URL/$gameId${params.buildEmbedInfo(true)}").body()
    }

    override suspend fun searchGames(
        search: String,
        query: QueryOrderBy?
    ): GameWrapper? {
        return client.get("$RUNNER_GAMES_URL?$PARAM_NAME=$search${query.buildOrderByInfo()}").body()
    }

    override suspend fun searchGames(
        name: String?,
        offset: Int?
    ): GameWrapper? {
        return client.get(getSearchGamesUrl(name, offset)).body()
    }

    private fun getSearchGamesUrl(name: String? = null, offset: Int? = null): String {
        val nameFilter = if (name != null) "?name=$name" else ""
        val offsetFilter = if (offset != null) "&offset=$offset" else ""
        return RUNNER_GAMES_URL + nameFilter + offsetFilter
    }

    companion object {
        private const val RUNNER_GAMES_URL = "games"
        private const val MODERATOR_PARAM = "moderator"
        private const val PARAM_NAME = "name"
    }
}