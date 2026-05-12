package com.dluche.myspeedrunners.data.datasource.model.leaderboard


import com.dluche.myspeedrunners.data.datasource.model.RunnerSearchWrapperDto
import com.dluche.myspeedrunners.data.datasource.model.category.CategoryEmbedDto
import com.dluche.myspeedrunners.data.datasource.model.common.LinkDto
import com.dluche.myspeedrunners.data.datasource.model.games.GameEmbedDto
import com.dluche.myspeedrunners.data.datasource.model.platform.PlatformWrapperDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardDto(
    @SerialName("category")
    val category: CategoryEmbedDto?,
    @SerialName("emulators")
    val emulators: Boolean?,
    @SerialName("game")
    val game: GameEmbedDto?,
    @SerialName("level")
    val level: String?,
    @SerialName("links")
    val links: List<LinkDto>?,
    @SerialName("platform")
    val platform: String?,
    @SerialName("platforms")
    val platforms: PlatformWrapperDto?,
    @SerialName("players")
    val players: RunnerSearchWrapperDto?,
    @SerialName("region")
    val region: String?,
    @SerialName("runs")
    val runs: List<LeaderboardRunDto>?,
    @SerialName("timing")
    val timing: String?,
    @SerialName("values")
    val values: HashMap<String,String>?,
    @SerialName("video-only")
    val videoOnly: Boolean?,
    @SerialName("weblink")
    val weblink: String?
) {
}
