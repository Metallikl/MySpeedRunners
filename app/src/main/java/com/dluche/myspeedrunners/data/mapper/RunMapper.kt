package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.personalbest.RunPbDto
import com.dluche.myspeedrunners.data.datasource.model.run.RunDto
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.run.RunStatusEnum
import com.dluche.myspeedrunners.extension.formatToDate
import java.time.format.DateTimeFormatter
import kotlin.time.Duration

fun RunDto.asDomainModel(): Run {
    return Run(
        id = this.id.orEmpty(),
        //category = this.categoryEmbed?.data.asDomainModel(),
        category = this.categoryEmbed.handleCategoryType(),
        comment = this.comment.orEmpty(),
        date = this.date?.formatToDate(dateFormatIn = DateTimeFormatter.ISO_DATE).orEmpty(),
        //game = this.gameEmbedDto?.data.asDomainModel(),
        game = this.gameEmbedDto.handleGameDtoType(),
        links = this.links?.mapToDomainLinks().orEmpty(),
        splits = this.splits?.mapToDomainLink(),
        submitted = this.submitted?.formatToDate().orEmpty(),
        videos = this.videos?.links?.asStringList().orEmpty(),
        weblink = this.weblink.orEmpty(),
        status = RunStatusEnum.fromString(this.status?.status),
        primaryTime = getPrimaryTime(this.times?.primary)
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
        primaryTime = getPrimaryTime(this.times?.primary)
    )
}

private fun getPrimaryTime(primary: String?): String {
    if(primary.isNullOrEmpty()) return ""
    return Duration.parseIsoStringOrNull(primary)?.toString().orEmpty()
}
