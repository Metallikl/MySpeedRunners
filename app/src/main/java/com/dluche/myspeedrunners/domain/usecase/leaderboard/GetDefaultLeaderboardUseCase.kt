package com.dluche.myspeedrunners.domain.usecase.leaderboard

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.leaderboard.Leaderboard

interface GetDefaultLeaderboardUseCase {
    suspend operator fun invoke(url:String,embedParams: EmbedParams? = null): Result<Leaderboard>
}