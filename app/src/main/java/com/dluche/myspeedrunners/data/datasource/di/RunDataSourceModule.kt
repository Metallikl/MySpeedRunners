package com.dluche.myspeedrunners.data.datasource.di

import com.dluche.myspeedrunners.data.datasource.run.RunDataSource
import com.dluche.myspeedrunners.data.datasource.run.RunDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RunDataSourceModule {
    @Binds
    fun bindRunDataSource(runDataSource: RunDataSourceImpl): RunDataSource

}