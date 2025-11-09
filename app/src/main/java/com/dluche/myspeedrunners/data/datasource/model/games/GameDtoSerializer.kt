package com.dluche.myspeedrunners.data.datasource.model.games// No mesmo arquivo: com/dluche/myspeedrunners/data/datasource/model/category/CategoryData.kt

import com.dluche.myspeedrunners.data.util.RequestConstants.DATA
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlin.collections.getValue

// O serializador customizado para a interface CategoryData
object GameDtoSerializer : KSerializer<GameDtoType> {

    // O descriptor pode ser o de um dos subtipos, pois a lógica real está no deserialize
    override val descriptor: SerialDescriptor = GameDtoType.GameID.serializer().descriptor


    override fun deserialize(decoder: Decoder): GameDtoType {
        // Garantimos que estamos trabalhando com um decodificador de JSON
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw IllegalStateException("This serializer can only be used with JSON format")

        // Decodificamos o elemento JSON como um "JsonElement" genérico
        val jsonElement = jsonDecoder.decodeJsonElement()

        // Agora, inspecionamos o tipo do elemento JSON
        return when (jsonElement) {
            // CASO 1: Se for um objeto JSON...
            is JsonObject -> {
                val rawDto =
                    if (jsonElement.containsKey(DATA)) {
                        // ...usamos o decodificador para transformá-lo em um CategoryDto
                        jsonDecoder.json.decodeFromJsonElement(
                            GameDto.serializer(),
                            jsonElement.getValue(DATA)
                        )
                    } else {
                        // ...usamos o decodificador para transformá-lo em um CategoryDto
                        jsonDecoder.json.decodeFromJsonElement(GameDto.serializer(), jsonElement)
                    }
                GameDtoType.GameObject(rawDto)
            }
            // CASO 2: Se for um primitivo (String, número, booleano)...
            is JsonPrimitive -> {
                // ...pegamos seu conteúdo como String
                GameDtoType.GameID(jsonElement.content)
            }
            // Outros casos (array, null) não são esperados aqui
            else -> throw IllegalStateException("Unexpected JSON element type for CategoryData")
        }
    }

    override fun serialize(encoder: Encoder, value: GameDtoType) {
        // Lógica para transformar o objeto de volta em JSON (se necessário)
        when (value) {
            is GameDtoType.GameObject -> {
                // Se for um objeto completo, serializa o DTO interno
                encoder.encodeSerializableValue(GameDto.serializer(), value.data)
            }

            is GameDtoType.GameID -> {
                // Se for apenas o ID, serializa a String
                encoder.encodeString(value.id)
            }

            is GameDtoType.GameEmbed -> {
                encoder.encodeSerializableValue(GameEmbedDto.serializer(), value.data)
            }
        }
    }
}
