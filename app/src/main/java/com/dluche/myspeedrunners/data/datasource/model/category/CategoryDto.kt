package com.dluche.myspeedrunners.data.datasource.model.category


import com.dluche.myspeedrunners.data.datasource.model.common.LinkDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("id")
    val id: String?,
    @SerialName("links")
    val links: List<LinkDto>?,
    @SerialName("miscellaneous")
    val miscellaneous: Boolean? = false,
    @SerialName("name")
    val name: String?,
    @SerialName("rules")
    val rules: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("weblink")
    val weblink: String?
)