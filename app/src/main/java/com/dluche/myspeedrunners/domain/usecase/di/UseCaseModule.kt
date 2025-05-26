package com.dluche.myspeedrunners.domain.usecase.di

import com.dluche.myspeedrunners.domain.usecase.game.GetRunnerGamesUseCase
import com.dluche.myspeedrunners.domain.usecase.game.GetRunnerGamesUseCaseImpl
import com.dluche.myspeedrunners.domain.usecase.run.GetRunByIdUseCase
import com.dluche.myspeedrunners.domain.usecase.run.GetRunByIdUseCaseImpl
import com.dluche.myspeedrunners.domain.usecase.run.GetRunnerRunsUseCase
import com.dluche.myspeedrunners.domain.usecase.run.GetRunnerRunsUseCaseImpl
import com.dluche.myspeedrunners.domain.usecase.runner.GetRunnerUseCase
import com.dluche.myspeedrunners.domain.usecase.runner.GetRunnerUseCaseImpl
import com.dluche.myspeedrunners.domain.usecase.runner.SearchRunnersUseCase
import com.dluche.myspeedrunners.domain.usecase.runner.SearchRunnersUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetRunnerUseCase(getRunnerUseCase: GetRunnerUseCaseImpl): GetRunnerUseCase

    @Binds
    fun bindGetRunnerRunsUseCase(getRunnerRunsUseCase: GetRunnerRunsUseCaseImpl): GetRunnerRunsUseCase

    @Binds
    fun bindGetRunnerGamesUseCase(getRunnerGamesUseCase: GetRunnerGamesUseCaseImpl): GetRunnerGamesUseCase

    @Binds
    fun bindSearchRunnersUseCase(searchRunnersUseCase: SearchRunnersUseCaseImpl): SearchRunnersUseCase

    @Binds
    fun bindGetRunByIdUseCase(getRunByIdUseCase: GetRunByIdUseCaseImpl): GetRunByIdUseCase


}