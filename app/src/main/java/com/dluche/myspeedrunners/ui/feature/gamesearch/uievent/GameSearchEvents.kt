package com.dluche.myspeedrunners.ui.feature.gamesearch.uievent

sealed interface GameSearchEvents {
    data class UpdateSearch(val search: String): GameSearchEvents
}