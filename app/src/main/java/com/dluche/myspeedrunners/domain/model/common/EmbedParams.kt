package com.dluche.myspeedrunners.domain.model.common

//todo refactor this to accept vararg params
data class EmbedParams(
    val param1: String = "",
    val param2: String = "",
    val param3: String = "",
) {
    companion object {
        const val EMBED_PARAM = "embed"
        const val GAMES = "game"
        const val CATEGORY = "category"
        const val PLATFORMS = "platforms"
        const val CATEGORIES = "categories"
        const val MODERATORS = "moderators"
    }
}