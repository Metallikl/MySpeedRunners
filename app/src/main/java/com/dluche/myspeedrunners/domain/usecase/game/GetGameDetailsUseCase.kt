package com.dluche.myspeedrunners.domain.usecase.game

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.game.Game

interface GetGameDetailsUseCase {
    suspend operator fun invoke(gameId: String,params: EmbedParams): Result<Game>
}