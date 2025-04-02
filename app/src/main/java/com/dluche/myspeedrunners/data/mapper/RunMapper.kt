package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.run.RunDto
import com.dluche.myspeedrunners.domain.model.run.Run

fun RunDto.asDomainModel(): Run {
    return Run(
        id = this.id.orEmpty(),
        category = this.categoryEmbed?.data.asDomainModel(),
        comment = this.comment.orEmpty(),
        date = this.date.orEmpty(),
        game = this.gameEmbedDto?.data.asDomainModel(),
        links = this.links?.mapToDomainLinks().orEmpty(),
        splits = this.splits.orEmpty(),
        submitted = this.submitted.orEmpty(),
        videos = this.videos?.links?.asStringList().orEmpty(),
        weblink = this.weblink.orEmpty(),
        status = this.status?.status.orEmpty()
    )
}
