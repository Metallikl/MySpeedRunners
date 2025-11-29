package com.dluche.myspeedrunners.data.datasource.model.category

import kotlinx.serialization.Serializable

@Serializable(with = CategoryDataSerializer::class)
sealed interface CategoryDtoType{
    @Serializable
    data class CategoryID(
        val id: String
    ):CategoryDtoType

    data class CategoryObject(
        val data: CategoryDto
    ): CategoryDtoType

    data class CategoryEmbed(
        val data: CategoryEmbedDto
    ): CategoryDtoType
}