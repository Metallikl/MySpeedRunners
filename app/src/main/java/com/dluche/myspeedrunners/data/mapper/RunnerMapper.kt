package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.ColorThemeDto
import com.dluche.myspeedrunners.data.datasource.model.NameStyleDto
import com.dluche.myspeedrunners.data.datasource.model.RunnerDto
import com.dluche.myspeedrunners.domain.model.runner.ColorTheme
import com.dluche.myspeedrunners.domain.model.runner.NameStyle
import com.dluche.myspeedrunners.domain.model.runner.NameStyleEnum
import com.dluche.myspeedrunners.domain.model.runner.RoleEnum
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.domain.model.runner.SocialNetwork
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType.SPEEDRUNSLIVE
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType.TWITCH
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType.TWITTER
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType.YOUTUBE
import com.dluche.myspeedrunners.extension.formatToDate

fun RunnerDto.asDomainModel() = Runner(
    id = this.id.orEmpty(),
    name = this.names?.international.orEmpty(),
    pronouns = this.pronouns,
    japaneseName = this.names?.japanese,
    location = getLocation(),
    locationUrl = getLocationUrl(),
    iconUrl = this.assets?.icon?.uri,
    imageUrl = this.assets?.image?.uri,
    webLink = this.weblink,
    socialNetworks = mapToDomainSocialNetworks(),
    nameStyle = nameStyle?.mapToDomainNameStyle(),
    links = links?.mapToDomainLinks(),
    role = RoleEnum.fromString(this.role),
    signup = this.signup?.formatToDate()
)

fun RunnerDto.asCardDomainModel() = RunnerCard(
    id = this.id.orEmpty(),
    name = this.names?.international.orEmpty(),
    pronouns = this.pronouns,
    japaneseName = this.names?.japanese,
    location = getLocation(),
    locationUrl = getLocationUrl(),
    imageUrl = this.assets?.image?.uri,
    nameStyle = nameStyle?.mapToDomainNameStyle(),
    role = RoleEnum.fromString(this.role),
    signup = this.signup?.formatToDate()
)



private fun RunnerDto.mapToDomainSocialNetworks(): List<SocialNetwork> {
    val socialNetworkList = mutableListOf<SocialNetwork>()
    weblink?.let{
        socialNetworkList.add(
            SocialNetwork(SocialNetworkType.SPEEDRUN_COM, it)
        )
    }
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

private fun RunnerDto.getLocation(): String {
    return this.location?.region?.names?.international
        ?: this.location?.country?.names?.international.orEmpty()
}

private fun RunnerDto.getLocationUrl(): String {
    val locationCode = this.location?.region?.code ?: this.location?.country?.code
    return locationCode?.let{
        "https://www.speedrun.com/images/flags/${it}.png"
    } ?: ""
}


