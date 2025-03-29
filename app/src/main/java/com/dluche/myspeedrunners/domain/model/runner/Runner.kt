package com.dluche.myspeedrunners.domain.model.runner

data class Runner(
    val id: String,
    val name: String,
    val pronouns: String?,
    val japaneseName: String?,
    val country: String?,
    val region: String?,
    val iconUrl: String?,
    val imageUrl: String?,
    val webLink: String?,
    val socialNetworks: List<SocialNetwork>?,
    val nameStyle: NameStyle?,
    val links: List<RunnerLink>?
)