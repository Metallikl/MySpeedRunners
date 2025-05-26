package com.dluche.myspeedrunners.extension

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration


@Composable
fun Modifier.shimmerEffect(showShimmer: Boolean = true) = composed {
    if(showShimmer) {
        val localConfig = LocalConfiguration.current
        val target = (localConfig.screenWidthDp * 4).toFloat()
        val infiniteTransition = rememberInfiniteTransition("shimmerEffect")
        val scale = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = target,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000, easing = LinearEasing)
            ),
            label = "shimmerScale"
        )
        val shimmerColors = listOf(
            Color.Gray.copy(alpha = 0.5f),
            Color.Gray.copy(alpha = 0.1f),
            Color.Gray.copy(alpha = 0.5f),
        )

        val brush = Brush.linearGradient(
            colors = shimmerColors,
            end = Offset(x = scale.value, y = scale.value)
        )

        background(brush)
    } else{
        background(Color.Unspecified)
    }
}