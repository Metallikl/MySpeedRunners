package com.dluche.myspeedrunners.data.datasource.model.personalbest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonalBestWrapperDto(
    @SerialName("data")
    val data: List<PersonalBestDto>?
)
