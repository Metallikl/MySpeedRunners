package com.dluche.myspeedrunners.data.datasource.model.leaderboard


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardDtoWrapper(
    @SerialName("data")
    val data: LeaderboardDto?
)