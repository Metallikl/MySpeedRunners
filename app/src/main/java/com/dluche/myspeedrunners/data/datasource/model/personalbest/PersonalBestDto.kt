package com.dluche.myspeedrunners.data.datasource.model.personalbest

import com.dluche.myspeedrunners.data.datasource.model.games.GameEmbedDto
import com.dluche.myspeedrunners.data.datasource.model.run.RunDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonalBestDto(
    @SerialName("place")
    val place: Int,
    @SerialName("run")
    val run: RunDto?,
    @SerialName("game")
    val game: GameEmbedDto?,
)
