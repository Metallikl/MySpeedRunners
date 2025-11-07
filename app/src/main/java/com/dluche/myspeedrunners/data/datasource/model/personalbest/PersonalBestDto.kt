package com.dluche.myspeedrunners.data.datasource.model.personalbest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonalBestDto(
    @SerialName("place")
    val place: Int,
    @SerialName("run")
    val run: RunPbDto?,
    @SerialName("game")
    val game: GamePbWrapper?,
//    @SerialName("category")
//    val category: CategoryDto?
)
