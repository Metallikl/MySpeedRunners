package com.dluche.myspeedrunners.domain.model.common

data class EmbedParams(
    val param1: String = "",
    val param2: String = ""
) {
    companion object {
        const val GAMES = "game"
        const val CATEGORY = "category"
    }
}