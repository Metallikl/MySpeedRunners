package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.games.GameDto
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.extension.formatToDate
import java.time.format.DateTimeFormatter

fun GameDto?.asDomainModel(): Game {
    this?.let{
        return Game(
            id = this.id.orEmpty(),
            name = this.names?.international.orEmpty(),
            imageUrl = this.assets?.coverLarge?.uri.orEmpty(),
            weblink = this.weblink.orEmpty(),
            releaseData = this.releaseDate?.formatToDate(dateFormatIn = DateTimeFormatter.ISO_DATE).orEmpty(),
            backgroundUrl = this.assets?.background?.uri.orEmpty(),
            links = this.links?.mapToDomainLinks().orEmpty()
        )
    } ?: return Game("", "", "", "", "", emptyList(), "")
}
