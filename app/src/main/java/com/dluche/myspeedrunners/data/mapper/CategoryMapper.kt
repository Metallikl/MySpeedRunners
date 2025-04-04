package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.category.CategoryDto
import com.dluche.myspeedrunners.domain.model.category.Category

fun CategoryDto?.asDomainModel(): Category {
    return this?.let{
        Category(
            id = this.id.orEmpty(),
            links = this.links?.mapToDomainLinks().orEmpty(),
            miscellaneous = this.miscellaneous == true,
            name = this.name.orEmpty(),
            rules = this.rules.orEmpty(),
            type = this.type.orEmpty(),
            weblink = this.weblink.orEmpty()
        )
    }?: Category("", emptyList(), false, "", "", "", "")
}