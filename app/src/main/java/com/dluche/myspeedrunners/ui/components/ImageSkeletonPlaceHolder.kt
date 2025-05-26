package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.valentinilk.shimmer.shimmer

@Composable

fun ImageSkeletonPlaceholder(
    modifier: Modifier = Modifier,
    size: Dp = 150.dp,
    clip: Shape? = null,
    backgroundColor: Color = Color.LightGray
) {
    Box(
        modifier = modifier
            .shimmer()
            .size(size)
            .apply {
                clip?.let { shape ->
                    clip(shape)
                }
            }
            .background(backgroundColor)
    )
}

@Preview
@Composable
private fun ImageSkeletonPlaceholderPreview() {
    MySpeedRunnersTheme {
        ImageSkeletonPlaceholder(clip = CircleShape)
    }
}