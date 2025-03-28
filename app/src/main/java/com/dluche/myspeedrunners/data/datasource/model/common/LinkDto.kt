package com.dluche.myspeedrunners.data.datasource.model.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkDto(
    @SerialName("rel")
    val rel: String? = null,
    @SerialName("uri")
    val uri: String? = null
)