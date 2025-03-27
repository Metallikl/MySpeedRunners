package com.dluche.myspeedrunners.data.datasource.model.run


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusDto(
    @SerialName("examiner")
    val examiner: String?,
    @SerialName("status")
    val status: String?,
    @SerialName("verify-date")
    val verifyDate: String?
)