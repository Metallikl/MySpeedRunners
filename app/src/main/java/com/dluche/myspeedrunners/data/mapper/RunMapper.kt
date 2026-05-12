package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.personalbest.RunPbDto
import com.dluche.myspeedrunners.data.datasource.model.run.RunDto
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.run.RunStatusEnum
import com.dluche.myspeedrunners.extension.formatToDate
import com.dluche.myspeedrunners.extension.orRandomId
import java.time.format.DateTimeFormatter
import kotlin.time.Duration

fun RunDto.asDomainModel(): Run {
    return Run(
        id = this.id.orEmpty(),
        category = this.categoryEmbed.handleCategoryType(),
        comment = this.comment.orEmpty(),
        date = this.date?.formatToDate(dateFormatIn = DateTimeFormatter.ISO_DATE).orEmpty(),
        game = this.gameEmbedDto.handleGameDtoType(),
        links = this.links?.mapToDomainLinks().orEmpty(),
        splits = this.splits?.mapToDomainLink(),
        submitted = this.submitted?.formatToDate().orEmpty(),
        videos = this.videos?.links?.asStringList().orEmpty(),
        weblink = this.weblink.orEmpty(),
        status = RunStatusEnum.fromString(this.status?.status),
        primaryTime = getPrimaryTime(this.times?.primary),
        runners = this.players.handlePlayerDtoType()
    )
}

fun RunPbDto.asDomainModel(): Run{
    return Run(
        id = this.id.orEmpty(),
        category = this.category.handleCategoryType(),
        comment = this.comment.orEmpty(),
        date = this.date?.formatToDate(dateFormatIn = DateTimeFormatter.ISO_DATE).orEmpty(),
        game = this.game.handleGameDtoType(),
        links = this.links?.mapToDomainLinks().orEmpty(),
        splits = this.splits?.mapToDomainLink(),
        submitted = this.submitted?.formatToDate().orEmpty(),
        videos = this.videos?.links?.asStringList().orEmpty(),
        weblink = this.weblink.orEmpty(),
        status = RunStatusEnum.fromString(this.status?.status),
        primaryTime = getPrimaryTime(this.times?.primary),
        runners = this.players.handlePlayerDtoType()
    )
}

private fun getPrimaryTime(primary: String?): String {
    if(primary.isNullOrEmpty()) return ""
    return Duration.parseIsoStringOrNull(primary)?.toString().orEmpty()
}

fun getEmptyRun() = Run(
    category = getEmptyCategory(),
    comment = "",
    date = "",
    game = getEmptyGame(),
    id = null.orRandomId(),
    links = emptyList(),
    splits = null,
    submitted = "",
    videos = emptyList(),
    weblink = "",
    status = RunStatusEnum.UNKNOWN,
    primaryTime = "",
    runners = emptyList()
)
