package com.dluche.myspeedrunners.data.datasource.model.run


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusDto(
    @SerialName("examiner")
    val examiner: String? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("verify-date")
    val verifyDate: String? = null
)