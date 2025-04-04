package com.dluche.myspeedrunners.data.datasource.model.run


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SystemDto(
    @SerialName("emulated")
    val emulated: Boolean?,
    @SerialName("platform")
    val platform: String?,
    @SerialName("region")
    val region: String?
)