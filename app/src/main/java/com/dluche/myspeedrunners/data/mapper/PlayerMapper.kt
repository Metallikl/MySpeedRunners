package com.dluche.myspeedrunners.data.mapper

import com.dluche.myspeedrunners.data.datasource.model.players.PlayerDtoType
import com.dluche.myspeedrunners.domain.model.runner.RoleEnum
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.extension.isNotNullNorEmpty

fun PlayerDtoType?.handlePlayerDtoType(): List<RunnerCard> {
    return when (this) {
        is PlayerDtoType.PlayerEmbed -> {
            this.data.data?.map {
                it.asCardDomainModel()
            } ?: emptyList()
        }

        is PlayerDtoType.PlayerItemObject -> {
            this.data.map {
                RunnerCard(
                    id = it.id.orEmpty(),
                    name = it.name.orEmpty(),
                    pronouns = null,
                    japaneseName = null,
                    location = null,
                    locationUrl = null,
                    imageUrl = null,
                    nameStyle = null,
                    role = if(it.id.isNotNullNorEmpty()) RoleEnum.USER else RoleEnum.GUEST,
                    signup = null
                )
            }
        }

        else -> emptyList()
    }
}
