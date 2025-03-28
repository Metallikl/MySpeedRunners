package com.dluche.myspeedrunners.domain.model.common

data class Pagination(
   val offset: Int = 0,
   val max: Int = 0,
   val size: Int = 0,
   val links: List<LinkModel> = emptyList()
)
