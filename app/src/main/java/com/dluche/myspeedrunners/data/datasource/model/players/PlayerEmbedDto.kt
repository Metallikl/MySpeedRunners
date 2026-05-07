package com.dluche.myspeedrunners.data.datasource.model.players


import com.dluche.myspeedrunners.data.datasource.model.RunnerDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerEmbedDto(
    @SerialName("data")
    val data: List<RunnerDto>? = null
)
