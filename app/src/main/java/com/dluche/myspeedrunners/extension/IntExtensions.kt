package com.dluche.myspeedrunners.extension

import kotlin.random.Random

fun Int?.orRandomId(): Int {
    return this ?: Random.nextInt(from = Int.MAX_VALUE / 2, until = Int.MAX_VALUE)
}
