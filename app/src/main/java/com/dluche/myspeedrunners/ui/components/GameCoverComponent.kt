package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.dluche.myspeedrunners.extension.HandleState
import com.valentinilk.shimmer.shimmer

@Composable
fun GameCoverComponent(
    imageUrl: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.FillBounds,
    aspectRatio: Float = 16 / 9f
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 16.dp)
    ) {
        if (!isLoading) {
            val coverPainter = rememberAsyncImagePainter(imageUrl)
            val coverPainterState = coverPainter.state.collectAsState()

            coverPainterState.value.HandleState(
                successContent = {
                    Image(
                        painter = coverPainter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(aspectRatio),
                        contentScale = contentScale,

                        )
                }
            )
        } else {
            Box(
                modifier = Modifier
                    .shimmer()
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }
    }
}