package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.run.RunWrapperDto
import com.dluche.myspeedrunners.domain.model.run.PaginatedRun

fun RunWrapperDto.asDomainModel() = PaginatedRun(
    data = this.data.map { it.asDomainModel() },
    pagination = this.pagination.asDomainModel()
)