package com.dluche.myspeedrunners.data.repository.di

import com.dluche.myspeedrunners.data.repository.LeaderboardRepositoryImpl
import com.dluche.myspeedrunners.domain.repository.LeaderboardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LeaderboardRepositoryModule {
    @Binds
    fun bindLeaderboardRepository(gamesRepository: LeaderboardRepositoryImpl): LeaderboardRepository
}