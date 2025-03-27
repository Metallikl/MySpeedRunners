package com.dluche.myspeedrunners.data.datasource.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RunnerWrapperDto(
    @SerialName("data")
    val wrapper: RunnerDto? = null
)

@Serializable
data class RunnerDto(
    @SerialName("assets")
    val assets: AssetsDto? = null,
    @SerialName("hitbox")
    val hitbox: UriDto? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("links")
    val links: List<LinkDto>? = null,
    @SerialName("location")
    val location: LocationDto? = null,
    @SerialName("name-style")
    val nameStyle: NameStyleDto? = null,
    @SerialName("names")
    val names: NamesDto? = null,
    @SerialName("pronouns")
    val pronouns: String? = null,
    @SerialName("role")
    val role: String? = null,
    @SerialName("signup")
    val signup: String? = null,
    @SerialName("speedrunslive")
    val speedRunsLive: UriDto? = null,
    @SerialName("supporterAnimation")
    val supporterAnimation: Boolean? = null,
    @SerialName("twitch")
    val twitch: UriDto? = null,
    @SerialName("twitter")
    val twitter: UriDto? = null,
    @SerialName("weblink")
    val weblink: String? = null,
    @SerialName("youtube")
    val youtube: UriDto? = null
)

@Serializable
data class AssetsDto(
    @SerialName("icon")
    val icon: UriDto? = null,
    @SerialName("image")
    val image: UriDto? = null,
    @SerialName("supporterIcon")
    val supporterIcon: UriDto? = null
)

@Serializable
data class LocationDto(
    @SerialName("country")
    val country: CountryDto? = null,
    @SerialName("region")
    val region: RegionDto? = null
)

@Serializable
data class CountryDto(
    @SerialName("code")
    val code: String? = null,
    @SerialName("names")
    val names: NamesDto?
)

@Serializable
data class RegionDto(
    @SerialName("code")
    val code: String? = null,
    @SerialName("names")
    val names: NamesDto? = null
)

@Serializable
data class NameStyleDto(
    @SerialName("color-from")
    val colorFrom: ColorThemeDto? = null,
    @SerialName("color-to")
    val colorTo: ColorThemeDto? = null,
    @SerialName("color")
    val color: ColorThemeDto? = null,
    @SerialName("style")
    val style: String? = null
)


@Serializable
data class ColorThemeDto(
    @SerialName("dark")
    val dark: String? = null,
    @SerialName("light")
    val light:String? = null
)

