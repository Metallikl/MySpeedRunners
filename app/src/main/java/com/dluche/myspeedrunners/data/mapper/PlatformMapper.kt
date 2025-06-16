package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.platform.PlatformDto
import com.dluche.myspeedrunners.domain.model.platform.Platform

fun PlatformDto.asDomainModel(): Platform {
    return Platform(
        id = this.id.orEmpty(),
        name = this.name.orEmpty(),
        released = this.released?.toIntOrNull() ?: 0,
        links = this.links?.mapToDomainLinks().orEmpty()
    )
}

fun List<PlatformDto>.asDomainModel(): List<Platform> {
    return this.map { it.asDomainModel() }
}