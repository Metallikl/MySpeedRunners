package com.dluche.myspeedrunners.domain.model.run

import com.dluche.myspeedrunners.domain.model.category.Category
import com.dluche.myspeedrunners.domain.model.common.LinkModel
import com.dluche.myspeedrunners.domain.model.game.Game

data class Run(
    val category: Category,
    val comment: String,
    val date: String,
    val game: Game,
    val id: String,
    val links: List<LinkModel>,
    val splits: LinkModel?,
    val submitted: String,
    val videos: List<String>,
    val weblink: String,
    val status: RunStatusEnum,
    val primaryTime: String,
)
