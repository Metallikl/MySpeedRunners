package com.dluche.myspeedrunners.data.datasource.runner

import com.dluche.myspeedrunners.data.datasource.model.PaginatedRunnersWrapperDto
import com.dluche.myspeedrunners.data.datasource.model.RunnerWrapperDto
import com.dluche.myspeedrunners.data.datasource.model.personalbest.PersonalBestWrapperDto
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy

interface RunnersDataSource {

    suspend fun searchRunners(
        name: String? = null,
        offset: Int? = null
    ): PaginatedRunnersWrapperDto?

    suspend fun getRunner(id: String): RunnerWrapperDto?

    suspend fun getRunnerPersonalBests(
        runnerId: String,
        embedParams: EmbedParams?,
        queryOrderBy: QueryOrderBy?
    ): PersonalBestWrapperDto?
}