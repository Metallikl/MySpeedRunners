package com.dluche.myspeedrunners.data.datasource.model.leaderboard

import com.dluche.myspeedrunners.data.datasource.model.run.RunDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardRunDto(
    @SerialName("place")
    val place: Int?,
    @SerialName("run")
    val run: RunDto?
)
