package com.dluche.myspeedrunners.data.datasource.model.run


import com.dluche.myspeedrunners.data.datasource.model.common.LinkDto
import com.dluche.myspeedrunners.data.datasource.model.games.GameEmbedDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunDto(
    @SerialName("category")
    val category: String? = null,
    @SerialName("comment")
    val comment: String? = null,
    @SerialName("date")
    val date: String? = null,
    @SerialName("game")
    val gameEmbedDto: GameEmbedDto? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("level")
    val level: String? = null,
    @SerialName("links")
    val links: List<LinkDto>? = null,
    @SerialName("players")
    val players: List<PlayerDto>? = null,
    @SerialName("splits")
    val splits: String? = null,
    @SerialName("status")
    val status: StatusDto? = null,
    @SerialName("submitted")
    val submitted: String? = null,
    @SerialName("system")
    val system: SystemDto? = null,
    @SerialName("times")
    val times: TimesDto? = null,
    @SerialName("videos")
    val videos: VideosDto? = null,
    @SerialName("weblink")
    val weblink: String? = null
)