package com.dluche.myspeedrunners.data.datasource.model.games

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameWrapper(
    @SerialName("data")
    val data: List<GameDto>,
)
