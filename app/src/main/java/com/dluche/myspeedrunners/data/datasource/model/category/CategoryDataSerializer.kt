package com.dluche.myspeedrunners.data.datasource.model.category// No mesmo arquivo: com/dluche/myspeedrunners/data/datasource/model/category/CategoryData.kt

import com.dluche.myspeedrunners.data.util.RequestConstants.DATA
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonObject

// O serializador customizado para a interface CategoryData
object CategoryDataSerializer : KSerializer<CategoryDtoType> {

    // O descriptor pode ser o de um dos subtipos, pois a lógica real está no deserialize
    override val descriptor: SerialDescriptor = CategoryDtoType.CategoryID.serializer().descriptor


    override fun deserialize(decoder: Decoder): CategoryDtoType {
        // Garantimos que estamos trabalhando com um decodificador de JSON
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw IllegalStateException("This serializer can only be used with JSON format")

        // Decodificamos o elemento JSON como um "JsonElement" genérico
        val jsonElement = jsonDecoder.decodeJsonElement()

        // Agora, inspecionamos o tipo do elemento JSON
        return when (jsonElement) {
            // CASO 1: Se for um objeto JSON...
            is kotlinx.serialization.json.JsonObject -> {
                val rawDto = if (jsonElement.containsKey(DATA)) {
                    jsonDecoder.json.decodeFromJsonElement(
                        CategoryDto.serializer(),
                        jsonElement.getValue(DATA)
                    )
                } else {
                    // ...usamos o decodificador para transformá-lo em um CategoryDto
                    jsonDecoder.json.decodeFromJsonElement(
                        CategoryDto.serializer(),
                        jsonElement
                    )
                }
                CategoryDtoType.CategoryObject(rawDto)
            }
            // CASO 2: Se for um primitivo (String, número, booleano)...
            is JsonPrimitive -> {
                // ...pegamos seu conteúdo como String
                CategoryDtoType.CategoryID(jsonElement.content)
            }
            // Outros casos (array, null) não são esperados aqui
            else -> throw IllegalStateException("Unexpected JSON element type for CategoryData")
        }
    }

    override fun serialize(encoder: Encoder, value: CategoryDtoType) {
        // Lógica para transformar o objeto de volta em JSON (se necessário)
        when (value) {
            is CategoryDtoType.CategoryObject -> {
                // Se for um objeto completo, serializa o DTO interno
                encoder.encodeSerializableValue(CategoryDto.serializer(), value.data)
            }

            is CategoryDtoType.CategoryID -> {
                // Se for apenas o ID, serializa a String
                encoder.encodeString(value.id)
            }

            is CategoryDtoType.CategoryEmbed -> {
                encoder.encodeSerializableValue(CategoryEmbedDto.serializer(), value.data)
            }
        }
    }
}
