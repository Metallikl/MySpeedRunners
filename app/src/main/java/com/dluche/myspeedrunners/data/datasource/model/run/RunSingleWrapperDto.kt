package com.dluche.myspeedrunners.data.datasource.model.run

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunSingleWrapperDto(
    @SerialName("data")
    val data: RunDto?
)
