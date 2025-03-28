package com.dluche.myspeedrunners.domain.model.run

import com.dluche.myspeedrunners.domain.model.common.Pagination

data class PaginatedRun(
    val data: List<Run>,
    val pagination: Pagination
)
