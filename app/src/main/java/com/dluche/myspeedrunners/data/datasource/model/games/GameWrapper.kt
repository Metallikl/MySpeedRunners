package com.dluche.myspeedrunners.data.datasource.model.games

import com.dluche.myspeedrunners.data.datasource.model.common.PaginationDto
import com.dluche.myspeedrunners.domain.model.common.Pagination
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameWrapper(
    @SerialName("data")
    val data: List<GameDto>? = null,
    @SerialName("pagination")
    val pagination: PaginationDto? = null
)
