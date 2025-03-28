package com.dluche.myspeedrunners.data.datasource.model.games


import com.dluche.myspeedrunners.data.datasource.model.common.UriDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssetsDto(
    @SerialName("background")
    val background: UriDto?,
    @SerialName("cover-large")
    val coverLarge: UriDto?,
    @SerialName("cover-medium")
    val coverMedium: UriDto?,
    @SerialName("cover-small")
    val coverSmall: UriDto?,
    @SerialName("cover-tiny")
    val coverTiny: UriDto?,
    @SerialName("foreground")
    val foreground: UriDto?,
    @SerialName("icon")
    val icon: UriDto?,
    @SerialName("logo")
    val logo: UriDto?,
    @SerialName("trophy-1st")
    val trophy1st: UriDto?,
    @SerialName("trophy-2nd")
    val trophy2nd: UriDto?,
    @SerialName("trophy-3rd")
    val trophy3rd: UriDto?,
    @SerialName("trophy-4th")
    val trophy4th: UriDto?
)