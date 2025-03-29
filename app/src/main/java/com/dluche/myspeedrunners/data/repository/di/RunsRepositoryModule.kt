package com.dluche.myspeedrunners.data.repository.di

import com.dluche.myspeedrunners.data.repository.RunsRepositoryImpl
import com.dluche.myspeedrunners.domain.repository.RunsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RunsRepositoryModule {

    @Binds
    fun bindRunsRepository(repository: RunsRepositoryImpl): RunsRepository
}