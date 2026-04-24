package com.dluche.myspeedrunners.domain.model.game

import com.dluche.myspeedrunners.domain.model.platform.Platform

data class GameCard(
    val id: String,
    val name: String,
    val imageUrl: String,
    val releaseData: String,
    val platforms: List<Platform> = emptyList(),
)
