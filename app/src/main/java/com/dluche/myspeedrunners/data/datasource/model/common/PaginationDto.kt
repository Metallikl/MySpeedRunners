package com.dluche.myspeedrunners.data.datasource.model.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationDto(
    @SerialName("offset")
   val offset: Int? = 0,
    @SerialName("max")
   val max: Int? = 0,
    @SerialName("size")
   val size: Int? = 0,
    @SerialName("links")
   val links: List<LinkDto>? = null
)
