package com.dluche.myspeedrunners.data.datasource.runner

import com.dluche.myspeedrunners.data.datasource.model.RunnerSearchWrapperDto
import com.dluche.myspeedrunners.data.datasource.model.RunnerWrapperDto

interface RunnersDataSource {

    suspend fun searchRunners(name: String? = null): RunnerSearchWrapperDto?

    suspend fun getRunner(id: String): RunnerWrapperDto?
}