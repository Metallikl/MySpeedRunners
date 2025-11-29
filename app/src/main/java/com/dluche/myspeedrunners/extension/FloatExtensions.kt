package com.dluche.myspeedrunners.extension

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun convertPixelToDp(pixelValue: Float): Dp {
    val density = LocalDensity.current
    return with(density) {
        pixelValue.toDp()
    }
}