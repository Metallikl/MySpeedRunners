package com.dluche.myspeedrunners.data.datasource.di

import com.dluche.myspeedrunners.data.datasource.leaderboard.LeaderboardDatasource
import com.dluche.myspeedrunners.data.datasource.leaderboard.LeaderboardDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface LeaderboardDatasourceModule {

    @Binds
    fun bindLeaderboardDatasource(leaderboardDatasource: LeaderboardDatasourceImpl): LeaderboardDatasource
}