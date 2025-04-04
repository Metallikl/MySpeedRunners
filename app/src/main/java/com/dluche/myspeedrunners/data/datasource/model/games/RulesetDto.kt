package com.dluche.myspeedrunners.data.datasource.model.games


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RulesetDto(
    @SerialName("default-time")
    val defaultTime: String?,
    @SerialName("emulators-allowed")
    val emulatorsAllowed: Boolean?,
    @SerialName("require-verification")
    val requireVerification: Boolean?,
    @SerialName("require-video")
    val requireVideo: Boolean?,
    @SerialName("run-times")
    val runTimes: List<String?>?,
    @SerialName("show-milliseconds")
    val showMilliseconds: Boolean?
)