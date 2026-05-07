package com.dluche.myspeedrunners.data.datasource.model.personalbest

import com.dluche.myspeedrunners.data.datasource.model.category.CategoryDtoType
import com.dluche.myspeedrunners.data.datasource.model.common.LinkDto
import com.dluche.myspeedrunners.data.datasource.model.games.GameDtoType
import com.dluche.myspeedrunners.data.datasource.model.players.PlayerDtoType
import com.dluche.myspeedrunners.data.datasource.model.players.PlayerRawDto
import com.dluche.myspeedrunners.data.datasource.model.run.StatusDto
import com.dluche.myspeedrunners.data.datasource.model.run.SystemDto
import com.dluche.myspeedrunners.data.datasource.model.run.TimesDto
import com.dluche.myspeedrunners.data.datasource.model.run.VideosDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunPbDto(
    @SerialName("category")
    val category: CategoryDtoType? = null,
    @SerialName("comment")
    val comment: String? = null,
    @SerialName("date")
    val date: String? = null,
    @SerialName("game")
    val game: GameDtoType? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("level")
    val level: String? = null,
    @SerialName("links")
    val links: List<LinkDto>? = null,
    @SerialName("players")
    val players: PlayerDtoType? = null,
    @SerialName("splits")
    val splits: LinkDto? = null,
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