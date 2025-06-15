package com.dluche.myspeedrunners.data.datasource.model.platform

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PlatformWrapperDto {
    @SerialName("data")
    val data: List<PlatformDto> = emptyList()
}