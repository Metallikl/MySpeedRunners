package com.dluche.myspeedrunners.data.datasource.di

import com.dluche.myspeedrunners.data.datasource.runner.RunnersDataSource
import com.dluche.myspeedrunners.data.datasource.runner.RunnersDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RunnersDataSourceModule {

    @Binds
    fun bindRunnersDataSource(runnersDataSource: RunnersDataSourceImpl): RunnersDataSource

}