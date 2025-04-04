package com.dluche.myspeedrunners.data.datasource.model.run

import com.dluche.myspeedrunners.data.datasource.model.common.PaginationDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunWrapperDto(
    @SerialName("data")
    val data: List<RunDto>,
    @SerialName("pagination")
    val pagination: PaginationDto? = null
)
