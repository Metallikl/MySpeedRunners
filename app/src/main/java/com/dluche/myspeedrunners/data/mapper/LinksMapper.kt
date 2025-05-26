package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.common.LinkDto
import com.dluche.myspeedrunners.domain.model.common.LinkModel

fun List<LinkDto>.mapToDomainLinks(): List<LinkModel> {
    return this.map {
        LinkModel(
            rel = it.rel,
            uri = it.uri
        )
    }
}

fun LinkDto.mapToDomainLink(): LinkModel {
    return LinkModel(
            rel = this.rel,
            uri = this.uri
        )
}