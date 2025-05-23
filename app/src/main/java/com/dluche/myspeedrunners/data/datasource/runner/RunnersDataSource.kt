package com.dluche.myspeedrunners.data.datasource.runner

import com.dluche.myspeedrunners.data.datasource.model.PaginatedRunnersWrapperDto
import com.dluche.myspeedrunners.data.datasource.model.RunnerWrapperDto

interface RunnersDataSource {

    suspend fun searchRunners(
        name: String? = null,
        offset: Int? = null
    ): PaginatedRunnersWrapperDto?

    suspend fun getRunner(id: String): RunnerWrapperDto?
}