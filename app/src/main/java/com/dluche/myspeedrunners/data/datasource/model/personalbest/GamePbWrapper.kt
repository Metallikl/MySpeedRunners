package com.dluche.myspeedrunners.data.datasource.model.personalbest

import com.dluche.myspeedrunners.data.datasource.model.games.GameDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GamePbWrapper(
    @SerialName("data")
    val data: GameDto,
)
