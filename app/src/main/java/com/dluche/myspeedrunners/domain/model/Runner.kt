package com.dluche.myspeedrunners.domain.model

data class Runner(
    val id: String,
    val name: String,
    val pronouns: String?,
    val japaneseName: String?,
    val country: String?,
    val region: String?,
    val imageUrl: String?,
    val webLink: String?,
    val socialLinks: List<SocialNetwork>?,
    val nameStyle: NameStyle?,
    val links: List<Link>?
)

data class SocialNetwork(
    val name: String,
    val url: String
)

data class NameStyle(
    val style: String?,
    val lightTheme: ColorFromTo?,
    val darkTheme: ColorFromTo?,

)

data class ColorFromTo(
    val colorFrom: String?,
    val colorTo: String?
)

data class Link(
    val rel: String?,
    val uri: String?
)

