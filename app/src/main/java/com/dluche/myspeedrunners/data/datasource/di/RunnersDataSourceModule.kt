package com.dluche.myspeedrunners.data.datasource.di

import com.dluche.myspeedrunners.data.datasource.RunnersDataSource
import com.dluche.myspeedrunners.data.datasource.RunnersDataSourceImpl
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