package com.dluche.myspeedrunners.data.repository.di

import com.dluche.myspeedrunners.data.repository.RunnersTempRepositoryImpl
import com.dluche.myspeedrunners.domain.repository.RunnersTempRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RunnerRepositoryTempModule{
    @Binds
    fun bindRunnerTempRepository(runnersTempRepository: RunnersTempRepositoryImpl): RunnersTempRepository
}