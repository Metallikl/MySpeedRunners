package com.dluche.myspeedrunners.domain.model.common

data class QueryOrderBy(
    val fieldToOrderBy: String,
    val direction: String = "desc"
){
    companion object{
        const val DATE = "date"
        const val DESC = "desc"
    }
}
