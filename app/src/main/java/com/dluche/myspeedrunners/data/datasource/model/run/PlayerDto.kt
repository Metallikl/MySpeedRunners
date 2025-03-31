package com.dluche.myspeedrunners.data.datasource.model.run


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("rel")
    val rel: String? = null,
    @SerialName("uri")
    val uri: String? = null
)