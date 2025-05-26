package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.run.RunDto
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.run.RunStatusEnum
import com.dluche.myspeedrunners.extension.formatToDate
import java.time.format.DateTimeFormatter

fun RunDto.asDomainModel(): Run {
    return Run(
        id = this.id.orEmpty(),
        category = this.categoryEmbed?.data.asDomainModel(),
        comment = this.comment.orEmpty(),
        date = this.date?.formatToDate(dateFormatIn = DateTimeFormatter.ISO_DATE).orEmpty(),
        game = this.gameEmbedDto?.data.asDomainModel(),
        links = this.links?.mapToDomainLinks().orEmpty(),
        splits = this.splits?.mapToDomainLink(),
        submitted = this.submitted?.formatToDate().orEmpty(),
        videos = this.videos?.links?.asStringList().orEmpty(),
        weblink = this.weblink.orEmpty(),
        status = RunStatusEnum.fromString(this.status?.status)
    )
}
