package com.dluche.myspeedrunners.domain.model.platform

import com.dluche.myspeedrunners.domain.model.common.LinkModel

data class Platform(
    val id: String,
    val links: List<LinkModel> = emptyList(),
    val name: String,
    val released: Int
)