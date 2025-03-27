package com.dluche.myspeedrunners.data.datasource.model.run


import com.dluche.myspeedrunners.data.datasource.model.LinkDto
import com.dluche.myspeedrunners.data.datasource.model.UriDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideosDto(
    @SerialName("links")
    val links: List<UriDto>?
)