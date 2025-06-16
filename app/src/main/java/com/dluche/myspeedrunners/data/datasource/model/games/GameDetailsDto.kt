package com.dluche.myspeedrunners.data.datasource.model.games


import com.dluche.myspeedrunners.data.datasource.model.RunnerSearchWrapperDto
import com.dluche.myspeedrunners.data.datasource.model.category.CategoryListEmbedDto
import com.dluche.myspeedrunners.data.datasource.model.common.LinkDto
import com.dluche.myspeedrunners.data.datasource.model.common.NamesDto
import com.dluche.myspeedrunners.data.datasource.model.platform.PlatformWrapperDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDetailsDto(
    @SerialName("abbreviation")
    val abbreviation: String? = null,
    @SerialName("assets")
    val assets: AssetsDto? = null,
    @SerialName("boostDistinctDonors")
    val boostDistinctDonors: Int? = null,
    @SerialName("boostReceived")
    val boostReceived: Int? = null,
    @SerialName("created")
    val created: String? = null,
    @SerialName("developers")
    val developers: List<String?>? = null,
    @SerialName("discord")
    val discord: String? = null,
    @SerialName("engines")
    val engines: List<String?>? = null,
    @SerialName("gametypes")
    val gametypes: List<String?>? = null,
    @SerialName("genres")
    val genres: List<String?>? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("links")
    val links: List<LinkDto>? = null,
    @SerialName("names")
    val names: NamesDto? = null,
    @SerialName("platforms")
    val platforms: PlatformWrapperDto? = null,
    @SerialName("publishers")
    val publishers: List<String?>? = null,
    @SerialName("regions")
    val regions: List<String?>? = null,
    @SerialName("release-date")
    val releaseDate: String? = null,
    @SerialName("released")
    val released: Int? = null,
    @SerialName("romhack")
    val romhack: Boolean? = null,
    @SerialName("ruleset")
    val ruleset: RulesetDto? = null,
    @SerialName("weblink")
    val weblink: String? = null,
    @SerialName("categories")
    val categories:CategoryListEmbedDto? = null,
    @SerialName("moderators")
    val moderator: RunnerSearchWrapperDto? = null,
)