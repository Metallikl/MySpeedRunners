package com.dluche.myspeedrunners.data.repository.di

import com.dluche.myspeedrunners.data.repository.RunnersRepositoryImpl
import com.dluche.myspeedrunners.domain.repository.RunnersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RunnerRepositoryModule{
    @Binds
    fun bindRunnerRepository(runnersRepository: RunnersRepositoryImpl): RunnersRepository

}