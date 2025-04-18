package com.dluche.myspeedrunners.data.datasource.model.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryWrapperDto(
    @SerialName("data")
    val data: List<CategoryDto>
)
