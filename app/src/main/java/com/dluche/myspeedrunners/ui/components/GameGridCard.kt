package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoodBad
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.ui.fake.game1
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.valentinilk.shimmer.shimmer

@Composable

fun GameGridCard(
    game: Game,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 150.dp
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick()
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            val painter = rememberAsyncImagePainter(game.imageUrl)
            val state = painter.state.collectAsState()

            when (state.value) {
                AsyncImagePainter.State.Empty,
                is AsyncImagePainter.State.Loading -> {
                    Box(
                        modifier = Modifier
                            .shimmer()
                            .size(size)
                            .background(Color.LightGray)

                    )
                }

                is AsyncImagePainter.State.Error -> {
                    //todo tratar o cenario de erro
                    Image(
                        imageVector = Icons.Default.MoodBad,
                        contentDescription = "Error loading image",
                        modifier = Modifier
                            .size(size),
                        colorFilter = ColorFilter.tint(Color.Red)

                    )
                }

                is AsyncImagePainter.State.Success -> {
                    Image(
                        painter = painter,
                        contentDescription = game.name,
                        modifier = Modifier
                            .size(150.dp),
                        contentScale = ContentScale.FillBounds,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun GameGridCardPreview() {
    MySpeedRunnersTheme {
        GameGridCard(
            game1,
            onClick = {}
        )
    }
}