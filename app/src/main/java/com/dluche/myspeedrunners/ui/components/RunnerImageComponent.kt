package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.dluche.myspeedrunners.R
import com.valentinilk.shimmer.shimmer

@Composable
fun RunnerImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    imageSize: Dp = 150.dp,
    imageErrorSize: Dp = 100.dp
) {
    val painter = rememberAsyncImagePainter(imageUrl)
    val state = painter.state.collectAsState()

    when (state.value) {
        AsyncImagePainter.State.Empty,
        is AsyncImagePainter.State.Loading -> {
            Box(
                modifier = modifier
                    .shimmer()
                    .size(imageSize)
                    .clip(CircleShape)
                    .background(Color.Gray)

            )
        }

        is AsyncImagePainter.State.Success -> {
            Image(
                painter = painter,
                contentDescription = "Runner Image",
                modifier = modifier
                    .size(imageSize)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        is AsyncImagePainter.State.Error -> {
            Image(
                painter = painterResource(R.drawable.ic_no_profile_image),
                contentDescription = "Runner Image",
                modifier = modifier
                    .size(imageErrorSize)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}