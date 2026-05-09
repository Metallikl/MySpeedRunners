package com.dluche.myspeedrunners.ui.fake

import com.dluche.myspeedrunners.domain.model.category.Category
import com.dluche.myspeedrunners.domain.model.platform.Platform

val categoryFake1 = Category(
    id = "AnyPct",
    links = emptyList(),
    name= "Any%",
    miscellaneous = false,
    rules = "rules",
    type = "type",
    weblink = "weblink"
)

val categoryFake2 = Category(
    id = "GlitchLess",
    links = emptyList(),
    name= "GlitchLess",
    miscellaneous = true,
    rules = "Run without any glitches",
    type = "type",
    weblink = "weblink"
)
val categoryFake3 = Category(
    id = "AllChapters",
    links = emptyList(),
    name= "All Chapters",
    miscellaneous = true,
    rules = "Run all chapers in game, including DLC",
    type = "type",
    weblink = "weblink"
)

val categoryFakeList = listOf(categoryFake1,categoryFake2,categoryFake3)