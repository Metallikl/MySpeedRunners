package com.dluche.myspeedrunners.domain.model.game

import com.dluche.myspeedrunners.domain.model.category.Category
import com.dluche.myspeedrunners.domain.model.common.LinkModel
import com.dluche.myspeedrunners.domain.model.platform.Platform
import com.dluche.myspeedrunners.domain.model.runner.Runner

data class Game(
    val id: String,
    val name: String,
    val imageUrl: String,
    val weblink: String,
    val releaseData: String,
    val links: List<LinkModel>,
    val backgroundUrl: String,
    val platforms: List<Platform> = emptyList(),
    val categories: List<Category> = emptyList(),
    val moderators: List<Runner> = emptyList()
)
