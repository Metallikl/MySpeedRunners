package com.dluche.myspeedrunners.data.datasource.model.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NamesDto(
    @SerialName("international")
    val international: String? = null,
    @SerialName("japanese")
    val japanese: String? = null
)