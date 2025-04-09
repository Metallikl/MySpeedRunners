package com.dluche.myspeedrunners.ui.fake

import com.dluche.myspeedrunners.domain.model.runner.ColorTheme
import com.dluche.myspeedrunners.domain.model.runner.NameStyle
import com.dluche.myspeedrunners.domain.model.runner.NameStyleEnum
import com.dluche.myspeedrunners.domain.model.runner.RoleEnum
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.model.runner.SocialNetwork
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType

val runner1 = Runner(
    id = "kjp1v74j",
    name = "Daniel Luche",
    pronouns = "He/ Him",
    japaneseName = "ダニエル・ルシェ",
    location = "São Paulo, Brazil",
    locationUrl = "https://www.speedrun.com/images/flags/br/sp.png",
    iconUrl = "https://www.speedrun.com/static/user/kjp1v74j/icon.png?v=8db2d00",
    imageUrl = "https://www.speedrun.com/static/user/kjp1v74j/image.png?v=8db2d00",
    webLink = "https://www.speedrun.com/user/DanielLuche",
    socialNetworks = listOf(
        SocialNetwork(
            SocialNetworkType.TWITTER,
            "https://www.speedrun.com/user/DanielLuche"
        ),
        SocialNetwork(
            SocialNetworkType.YOUTUBE,
            "https://www.speedrun.com/user/DanielLuche"
        ),
        SocialNetwork(
            SocialNetworkType.TWITCH,
            "https://www.speedrun.com/user/DanielLuche"
        )
    ),
    nameStyle = NameStyle(
        style = NameStyleEnum.SOLID,
        color = ColorTheme(
            light = "#FF0000",
            dark = "#00FF00"
        ),
        colorFrom = null,
        colorTo = null
    ),
    links = listOf(),
    role = RoleEnum.USER,
    signup = "2025-04-04"
)