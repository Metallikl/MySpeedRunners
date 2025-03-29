package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.common.UriDto

fun List<UriDto>.asStringList(): List<String>{
    return this.map {
        it.uri.orEmpty()
    }
}