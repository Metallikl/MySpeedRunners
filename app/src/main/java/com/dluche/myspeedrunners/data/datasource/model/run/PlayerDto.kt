package com.dluche.myspeedrunners.data.datasource.model.run


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    @SerialName("id")
    val id: String?,
    @SerialName("rel")
    val rel: String?,
    @SerialName("uri")
    val uri: String?
)