package com.dluche.myspeedrunners.domain.model.runner

import com.dluche.myspeedrunners.domain.model.common.LinkModel

data class Runner(
    val id: String,
    val name: String,
    val pronouns: String?,
    val japaneseName: String?,
    val location: String?,
    val locationUrl: String?,
    val iconUrl: String?,
    val imageUrl: String?,
    val webLink: String?,
    val socialNetworks: List<SocialNetwork>?,
    val nameStyle: NameStyle?,
    val links: List<LinkModel>?,
    val role: String?,
    val signup: String?
)