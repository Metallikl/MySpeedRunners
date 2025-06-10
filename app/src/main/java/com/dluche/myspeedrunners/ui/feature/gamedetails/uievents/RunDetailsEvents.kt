package com.dluche.myspeedrunners.ui.feature.gamedetails.uievents

sealed interface GameDetailsEvents {
    object LoadRunDetails : GameDetailsEvents
}