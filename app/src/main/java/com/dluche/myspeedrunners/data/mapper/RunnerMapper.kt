package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.ColorThemeDto
import com.dluche.myspeedrunners.data.datasource.model.common.LinkDto
import com.dluche.myspeedrunners.data.datasource.model.NameStyleDto
import com.dluche.myspeedrunners.data.datasource.model.RunnerDto
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType.HITBOX
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType.SPEEDRUNSLIVE
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType.TWITCH
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType.TWITTER
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType.YOUTUBE
import com.dluche.myspeedrunners.domain.model.runner.ColorTheme
import com.dluche.myspeedrunners.domain.model.common.LinkModel
import com.dluche.myspeedrunners.domain.model.runner.NameStyle
import com.dluche.myspeedrunners.domain.model.runner.NameStyleEnum
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.model.runner.SocialNetwork

fun RunnerDto.asDomainModel() = Runner(
    id = this.id.orEmpty(),
    name = this.names?.international.orEmpty(),
    pronouns = this.pronouns,
    japaneseName = this.names?.japanese,
    country = this.location?.country?.names?.international,
    region = this.location?.region?.names?.international,
    iconUrl = this.assets?.icon?.uri,
    imageUrl = this.assets?.image?.uri,
    webLink = this.weblink,
    socialNetworks = mapToDomainSocialNetworks(),
    nameStyle = nameStyle?.mapToDomainNameStyle(),
    links = links?.mapToDomainLinks()
)

private fun RunnerDto.mapToDomainSocialNetworks(): List<SocialNetwork> {
    val socialNetworkList = mutableListOf<SocialNetwork>()
    twitch?.let {
        socialNetworkList.add(
            SocialNetwork(TWITCH, it.uri.orEmpty())
        )
    }
    twitter?.let {
        socialNetworkList.add(
            SocialNetwork(TWITTER, it.uri.orEmpty())
        )
    }
    hitbox?.let {
        socialNetworkList.add(
            SocialNetwork(HITBOX, it.uri.orEmpty())
        )
    }
    youtube?.let {
        socialNetworkList.add(
            SocialNetwork(YOUTUBE, it.uri.orEmpty())
        )
    }
    speedRunsLive?.let {
        socialNetworkList.add(
            SocialNetwork(SPEEDRUNSLIVE, it.uri.orEmpty())
        )
    }
    return socialNetworkList
}

private fun NameStyleDto.mapToDomainNameStyle() = NameStyle(
    style = this.style?.let { NameStyleEnum.parse(this.style) } ?: NameStyleEnum.UNKNOWN,
    color = this.color?.mapColorTheme(),
    colorFrom = this.colorFrom?.mapColorTheme(),
    colorTo = this.colorTo?.mapColorTheme()
)


private fun ColorThemeDto.mapColorTheme() = ColorTheme(
    light = this.light,
    dark = this.dark
)

private fun List<LinkDto>.mapToDomainLinks(): List<LinkModel> {
    return this.map {
        LinkModel(
            rel = it.rel,
            uri = it.uri
        )
    }
}