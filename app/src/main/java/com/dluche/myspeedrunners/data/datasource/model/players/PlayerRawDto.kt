package com.dluche.myspeedrunners.data.datasource.model.players


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerRawDto(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("rel")
    val rel: String? = null,
    @SerialName("uri")
    val uri: String? = null,
)