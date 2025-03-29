package com.dluche.myspeedrunners.data.datasource

import com.dluche.myspeedrunners.data.datasource.model.RunnerWrapperDto

interface RunnersDataSource {

    suspend fun getRunners(): List<RunnerWrapperDto>?

    suspend fun getRunner(id: String): RunnerWrapperDto?
}