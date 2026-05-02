package com.dluche.myspeedrunners.domain.usecase.game

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.game.GameCard

interface GetGamesFromPersonalBestUseCase {
    suspend operator fun invoke(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): Result<List<GameCard>>
}