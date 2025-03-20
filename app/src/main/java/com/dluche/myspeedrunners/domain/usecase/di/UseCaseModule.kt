package com.dluche.myspeedrunners.domain.usecase.di

import com.dluche.myspeedrunners.domain.usecase.GetRunnerUseCase
import com.dluche.myspeedrunners.domain.usecase.GetRunnerUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindGetRunnerUseCase(getRunnerUseCase: GetRunnerUseCaseImpl): GetRunnerUseCase
}