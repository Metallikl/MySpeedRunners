package com.dluche.myspeedrunners.domain.model.common

data class QueryOrderBy(
    val fieldToOrderBy: String,
    val direction: String = "desc"
)
