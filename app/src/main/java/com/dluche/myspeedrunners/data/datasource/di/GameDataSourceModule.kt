package com.dluche.myspeedrunners.data.datasource.di

import com.dluche.myspeedrunners.data.datasource.game.GameDataSource
import com.dluche.myspeedrunners.data.datasource.game.GameDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface GameDataSourceModule {

    @Binds
    fun bindGameDataSource(gameDataSource: GameDataSourceImpl): GameDataSource
}