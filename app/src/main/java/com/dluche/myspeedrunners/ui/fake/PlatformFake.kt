package com.dluche.myspeedrunners.ui.fake

import com.dluche.myspeedrunners.domain.model.platform.Platform

val platformFake1 = Platform(
    id = "PC",
    name= "PC",
    released = 22011988
)

val platformFake2 = Platform(
    id = "PS5",
    name= "PS5",
    released = 22011988
)

val platformFake3 = Platform(
    id = "XBOXS",
    name= "XBOX-S",
    released = 22011988
)

val platformFakeList = listOf(platformFake1,platformFake2,platformFake3)