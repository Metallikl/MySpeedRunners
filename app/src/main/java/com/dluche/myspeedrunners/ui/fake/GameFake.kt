package com.dluche.myspeedrunners.ui.fake

import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.game.GameCard

val game1 = Game(
    id = "1",
    name = "Pokemon TCG",
    imageUrl = "https://www.speedrun.com/static/game/pd0qq31e/cover?v=8b6ea7d",
    weblink = "https://www.speedrun.com/super_mario_odyssey",
    releaseData = "2017-09-23T23:05:37Z",
    backgroundUrl = "https://www.speedrun.com/static/game/pd0qq31e/background?v=8b6ea7d",
    links = emptyList(),
    discord = "https://discord.gg/92xARJJsWg"
)

val game2 = Game(
    id = "2",
    name = "Alex Kidd 3 - Curse in Miracle World",
    imageUrl = "https://www.speedrun.com/static/game/pdv9v5k1/cover?v=5b8577e",
    weblink = "https://www.speedrun.com/super_mario_odyssey",
    releaseData = "2024-07-19T23:40:52Z",
    backgroundUrl = "https://www.speedrun.com/static/game/pdv9v5k1/cover?v=5b8577e",
    links = emptyList(),
    discord = "https://discord.gg/92xARJJsWg"
)

val gameCard1 = GameCard(
    id = "1",
    name = "Pokemon TCG",
    imageUrl = "https://www.speedrun.com/static/game/pd0qq31e/cover?v=8b6ea7d",
    releaseData = "2017-09-23T23:05:37Z",
)

val gameCard2 = GameCard(
    id = "2",
    name = "Alex Kidd 3 - Curse in Miracle World",
    imageUrl = "https://www.speedrun.com/static/game/pdv9v5k1/cover?v=5b8577e",
    releaseData = "2024-07-19T23:40:52Z",
)

