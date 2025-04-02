package com.dluche.myspeedrunners.domain.model.category

import com.dluche.myspeedrunners.domain.model.common.LinkModel

data class Category(
    val id: String,
    val links: List<LinkModel>,
    val miscellaneous: Boolean,
    val name: String,
    val rules: String,
    val type: String,
    val weblink: String
)