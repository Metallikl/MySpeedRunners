package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.games.GameDetailsDto
import com.dluche.myspeedrunners.data.datasource.model.games.GameDto
import com.dluche.myspeedrunners.data.datasource.model.games.GameDtoType
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.game.GameCard
import com.dluche.myspeedrunners.extension.formatToDate
import java.time.format.DateTimeFormatter

fun GameDto?.asDomainModel(): Game {
    this?.let {
        return Game(
            id = it.id.orEmpty(),
            name = it.names?.international.orEmpty(),
            imageUrl = it.assets?.coverLarge?.uri.orEmpty(),
            weblink = it.weblink.orEmpty(),
            releaseData = it.releaseDate?.formatToDate(dateFormatIn = DateTimeFormatter.ISO_DATE)
                .orEmpty(),
            backgroundUrl = it.assets?.background?.uri.orEmpty(),
            links = it.links?.mapToDomainLinks().orEmpty(),
            platforms = emptyList(),
            moderators = emptyList(),
            categories = emptyList(),
            discord = it.discord.orEmpty()
        )
    } ?: return getEmptyGame()
}

fun GameDto?.asCardDomainModel(): GameCard {
    this?.let {
        return GameCard(
            id = it.id.orEmpty(),
            name = it.names?.international.orEmpty(),
            imageUrl = it.assets?.coverLarge?.uri.orEmpty(),
            releaseData = it.releaseDate?.formatToDate(dateFormatIn = DateTimeFormatter.ISO_DATE)
                .orEmpty()
        )
    } ?: return getEmptyGameCard()
}

fun GameDetailsDto?.asDomainModel(): Game {
    this?.let {
        return Game(
            id = it.id.orEmpty(),
            name = it.names?.international.orEmpty(),
            imageUrl = it.assets?.coverLarge?.uri.orEmpty(),
            weblink = it.weblink.orEmpty(),
            releaseData = it.releaseDate?.formatToDate(dateFormatIn = DateTimeFormatter.ISO_DATE)
                .orEmpty(),
            backgroundUrl = it.assets?.background?.uri.orEmpty(),
            links = it.links?.mapToDomainLinks().orEmpty(),
            platforms = it.platforms?.data?.asDomainModel().orEmpty(),
            categories = it.categories?.data?.map { it.asDomainModel() }.orEmpty(),
            moderators = it.moderator?.wrapper?.map { it.asCardDomainModel() }.orEmpty(),
            discord = it.discord.orEmpty()
        )
    } ?: return getEmptyGame()
}

fun getEmptyGame(id: String = ""): Game =
    Game(
        id = "",
        name = "",
        imageUrl = "",
        weblink = "",
        releaseData = "",
        links = emptyList(),
        backgroundUrl = "",
        platforms = emptyList(),
        categories = emptyList(),
        moderators = emptyList(),
        discord = ""
    )

fun GameDtoType?.handleGameDtoType(): Game {
    return when (this) {
        is GameDtoType.GameID -> getEmptyGame(id = this.id)
        is GameDtoType.GameObject -> {
            this.data.asDomainModel()
        }
        is GameDtoType.GameEmbed ->{
            this.data.data.asDomainModel()
        }
        null -> getEmptyGame()
    }
}

fun getEmptyGameCard(): GameCard=
    GameCard("", "", "", "")