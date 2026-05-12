package com.dluche.myspeedrunners.domain.usecase.leaderboard

import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.leaderboard.Leaderboard
import com.dluche.myspeedrunners.domain.repository.LeaderboardRepository
import javax.inject.Inject

class GetDefaultLeaderboardUseCaseImpl @Inject constructor(
    private val leaderboardRepository: LeaderboardRepository
): GetDefaultLeaderboardUseCase {
    override suspend fun invoke(url: String, embedParams: EmbedParams?): Result<Leaderboard> {
        return leaderboardRepository.fetchDefaultLeaderboard(url,embedParams)
    }
}