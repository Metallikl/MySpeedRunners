package com.dluche.myspeedrunners.data.repository.di

import com.dluche.myspeedrunners.data.repository.GamesRepositoryImpl
import com.dluche.myspeedrunners.domain.repository.GamesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface GamesRepositoryModule {

    @Binds
    fun bindGamesRepository(gamesRepository: GamesRepositoryImpl): GamesRepository

}