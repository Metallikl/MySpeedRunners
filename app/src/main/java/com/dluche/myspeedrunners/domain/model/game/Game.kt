package com.dluche.myspeedrunners.domain.model.game

import com.dluche.myspeedrunners.domain.model.common.LinkModel

data class Game(
    val id: String,
    val name: String,
    val imageUrl: String,
    val weblink: String,
    val releaseData: String,
    val links: List<LinkModel>,
    val backgroundUrl: String
)
