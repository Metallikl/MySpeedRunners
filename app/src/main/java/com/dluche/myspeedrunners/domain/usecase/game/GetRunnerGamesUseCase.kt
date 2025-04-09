package com.dluche.myspeedrunners.domain.usecase.game

import com.dluche.myspeedrunners.domain.model.game.Game

interface GetRunnerGamesUseCase {

    suspend operator fun invoke(runnerId: String): Result<List<Game>>
}