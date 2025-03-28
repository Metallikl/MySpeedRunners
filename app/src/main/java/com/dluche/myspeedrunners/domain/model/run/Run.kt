package com.dluche.myspeedrunners.domain.model.run

import com.dluche.myspeedrunners.data.datasource.model.run.PlayerDto
import com.dluche.myspeedrunners.data.datasource.model.run.SystemDto
import com.dluche.myspeedrunners.data.datasource.model.run.TimesDto
import com.dluche.myspeedrunners.domain.model.common.LinkModel

data class Run(
    val category: String?,
    val comment: String?,
    val date: String?,
    val game: String?,
    val id: String?,
    val level: String?,
    val links: List<LinkModel>?,
    val players: List<PlayerDto>?,
    val splits: String?,
    val submitted: String?,
    val system: SystemDto?,
    val times: TimesDto?,
    val videos: List<String>,
    val weblink: String?
)
