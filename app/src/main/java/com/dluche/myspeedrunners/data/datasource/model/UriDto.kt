package com.dluche.myspeedrunners.data.datasource.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UriDto(
    @SerialName("uri")
    val uri: String? = null
)