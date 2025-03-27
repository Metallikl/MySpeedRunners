package com.dluche.myspeedrunners.data.datasource.model.games


import com.dluche.myspeedrunners.data.datasource.model.LinkDto
import com.dluche.myspeedrunners.data.datasource.model.NamesDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDto(
    @SerialName("abbreviation")
    val abbreviation: String?,
    @SerialName("assets")
    val assets: AssetsDto?,
    @SerialName("boostDistinctDonors")
    val boostDistinctDonors: Int?,
    @SerialName("boostReceived")
    val boostReceived: Int?,
    @SerialName("created")
    val created: String?,
    @SerialName("developers")
    val developers: List<String?>?,
    @SerialName("discord")
    val discord: String?,
    @SerialName("engines")
    val engines: List<String?>?,
    @SerialName("gametypes")
    val gametypes: List<String?>?,
    @SerialName("genres")
    val genres: List<String?>?,
    @SerialName("id")
    val id: String?,
    @SerialName("links")
    val links: List<LinkDto?>?,
    @SerialName("names")
    val names: NamesDto?,
    @SerialName("platforms")
    val platforms: List<String?>?,
    @SerialName("publishers")
    val publishers: List<String?>?,
    @SerialName("regions")
    val regions: List<String?>?,
    @SerialName("release-date")
    val releaseDate: String?,
    @SerialName("released")
    val released: Int?,
    @SerialName("romhack")
    val romhack: Boolean?,
    @SerialName("ruleset")
    val ruleset: RulesetDto?,
    @SerialName("weblink")
    val weblink: String?
)