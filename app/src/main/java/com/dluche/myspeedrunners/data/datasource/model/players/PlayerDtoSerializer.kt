package com.dluche.myspeedrunners.data.datasource.model.players

import com.dluche.myspeedrunners.data.util.RequestConstants.DATA
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement

// O serializador customizado para a interface CategoryData
object PlayerDtoSerializer : KSerializer<PlayerDtoType> {

    // O descriptor pode ser o de um dos subtipos, pois a lógica real está no deserialize
    override val descriptor: SerialDescriptor = PlayerDtoType.PlayerID.serializer().descriptor
    override fun deserialize(decoder: Decoder): PlayerDtoType {
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw IllegalStateException("This serializer can only be used with JSON format")

        return when (val jsonElement = jsonDecoder.decodeJsonElement()) {
            is JsonArray -> {
                val list = jsonDecoder.json.decodeFromJsonElement<List<PlayerRawDto>>(jsonElement)
                PlayerDtoType.PlayerItemObject(list)
            }

            is JsonObject -> {
                if (jsonElement.containsKey(DATA)) {
                    PlayerDtoType.PlayerEmbed(
                        jsonDecoder.json.decodeFromJsonElement<PlayerEmbedDto>(
                            jsonElement
                        )
                    )
                } else {
                    throw IllegalStateException("Unexpected JSON element type for PlayerData")
                }
            }

            else -> throw IllegalStateException("Unexpected JSON element type for PlayerData")
        }
    }

    override fun serialize(encoder: Encoder, value: PlayerDtoType) {
        // Lógica para transformar o objeto de volta em JSON (se necessário)
        when (value) {
            is PlayerDtoType.PlayerItemObject -> {
                encoder.encodeSerializableValue(
                    PlayerRawDto.serializer(),
                    value.data[0]
                )
            }

            is PlayerDtoType.PlayerEmbed -> {
                encoder.encodeSerializableValue(PlayerEmbedDto.serializer(), value.data)
            }

            is PlayerDtoType.PlayerID ->  {
                encoder.encodeString(value.id)
            }
        }
    }
}
