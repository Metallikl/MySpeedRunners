package com.dluche.myspeedrunners.domain.model.runner

data class RunnerCard(
    val id: String,
    val name: String,
    val pronouns: String?,
    val japaneseName: String?,
    val location: String?,
    val locationUrl: String?,
    val imageUrl: String?,
    val nameStyle: NameStyle?,
    val role: RoleEnum,
    val signup: String?
)
