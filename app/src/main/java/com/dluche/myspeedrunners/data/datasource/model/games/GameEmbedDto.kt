package com.dluche.myspeedrunners.data.datasource.model.games

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameEmbedDto(
    @SerialName("data")
    val data: GameDto,
)
