package com.dluche.myspeedrunners.data.datasource.model.run


import com.dluche.myspeedrunners.data.datasource.model.LinkDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunDto(
    @SerialName("category")
    val category: String?,
    @SerialName("comment")
    val comment: String?,
    @SerialName("date")
    val date: String?,
    @SerialName("game")
    val game: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("level")
    val level: String?,
    @SerialName("links")
    val links: List<LinkDto>?,
    @SerialName("players")
    val players: List<PlayerDto>?,
    @SerialName("splits")
    val splits: String?,
    @SerialName("status")
    val status: StatusDto?,
    @SerialName("submitted")
    val submitted: String?,
    @SerialName("system")
    val system: SystemDto?,
    @SerialName("times")
    val times: TimesDto?,
    @SerialName("videos")
    val videos: VideosDto?,
    @SerialName("weblink")
    val weblink: String?
)