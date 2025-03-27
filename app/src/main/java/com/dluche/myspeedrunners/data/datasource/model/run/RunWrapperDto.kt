package com.dluche.myspeedrunners.data.datasource.model.run

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunWrapperDto(
    @SerialName("data")
    val data: List<RunDto>
)
