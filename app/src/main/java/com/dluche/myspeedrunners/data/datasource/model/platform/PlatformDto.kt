package com.dluche.myspeedrunners.data.datasource.model.platform


import com.dluche.myspeedrunners.data.datasource.model.common.LinkDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlatformDto(
    @SerialName("id")
    val id: String?,
    @SerialName("links")
    val links: List<LinkDto>?,
    @SerialName("name")
    val name: String?,
    @SerialName("released")
    val released: String?
)