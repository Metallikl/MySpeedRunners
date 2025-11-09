package com.dluche.myspeedrunners.data.datasource.model.games

import kotlinx.serialization.Serializable

@Serializable(with = GameDtoSerializer::class)
sealed interface GameDtoType {
    @Serializable
    data class GameID(val id: String) : GameDtoType
    data class GameObject(val data: GameDto) : GameDtoType
    data class GameEmbed(val data: GameEmbedDto) : GameDtoType
}