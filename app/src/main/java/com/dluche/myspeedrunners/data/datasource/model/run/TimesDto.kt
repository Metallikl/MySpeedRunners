package com.dluche.myspeedrunners.data.datasource.model.run


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimesDto(
    @SerialName("ingame")
    val ingame: String?,
    @SerialName("ingame_t")
    val ingameT: Float?,
    @SerialName("primary")
    val primary: String?,
    @SerialName("primary_t")
    val primaryT: Float?,
    @SerialName("realtime")
    val realtime: String?,
    @SerialName("realtime_noloads")
    val realtimeNoloads: String?,
    @SerialName("realtime_noloads_t")
    val realtimeNoloadsT: Float?,
    @SerialName("realtime_t")
    val realtimeT: Float?
)