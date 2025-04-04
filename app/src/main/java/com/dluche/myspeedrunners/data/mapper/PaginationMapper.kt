package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.common.PaginationDto
import com.dluche.myspeedrunners.domain.model.common.Pagination

fun PaginationDto?.asDomainModel() = this?.let {
    Pagination(
        offset = this.offset ?: 0,
        max = this.max ?: 0,
        size = this.size ?: 0,
        links = this.links?.mapToDomainLinks().orEmpty()
    )
} ?: Pagination()
