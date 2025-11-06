package com.dluche.myspeedrunners.domain.model.common

data class EmbedParams(
    val params: List<String> = emptyList(),
) {
    companion object {
        const val EMBED_PARAM = "embed"
        const val GAMES = "game"
        const val CATEGORY = "category"
        const val PLATFORMS = "platforms"
        const val CATEGORIES = "categories"
        const val MODERATORS = "moderators"
    }

    constructor(vararg params: String) : this(params.toList())
}
