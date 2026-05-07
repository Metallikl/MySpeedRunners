package com.dluche.myspeedrunners.data.datasource.model.players

import kotlinx.serialization.Serializable

@Serializable(with = PlayerDtoSerializer::class)
sealed interface PlayerDtoType {

    @Serializable
    data class PlayerID(val id: String) : PlayerDtoType
    data class PlayerItemObject(val data: List<PlayerRawDto>) : PlayerDtoType
    data class PlayerEmbed(val data: PlayerEmbedDto) : PlayerDtoType
}