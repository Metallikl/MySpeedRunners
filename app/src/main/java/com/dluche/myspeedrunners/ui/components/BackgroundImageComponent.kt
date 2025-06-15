package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import coil3.compose.rememberAsyncImagePainter
import com.dluche.myspeedrunners.extension.HandleState
import com.valentinilk.shimmer.shimmer

@Composable
fun BackgroundImageComponent(
    backgroundUrl: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
) {
    val backgroundPainter = rememberAsyncImagePainter(backgroundUrl)
    val backgroundPainterState = backgroundPainter.state.collectAsState()

    backgroundPainterState.value.HandleState(
        successContent = {
            Image(
                painter = backgroundPainter,
                contentDescription = null,
                modifier = modifier
                    .fillMaxSize()
                    .alpha(0.4f),
                contentScale = contentScale,
            )
        },
        errorContent = {
            Image(
                painter = backgroundPainter,
                contentDescription = null,
                modifier = modifier.fillMaxSize()
            )
        },
        loadingContent = {
            Box(
                modifier = Modifier
                    .shimmer()
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            )
        }
    )
}