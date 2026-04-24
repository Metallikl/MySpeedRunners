package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun GameCardComponent(
    gameUrl: String,
    gameName: String,
    releaseData: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .heightIn(max = 118.dp),
        ) {

            GameImage(
                gameUrl = gameUrl,
                contentDescription = contentDescription,
                size = 100.dp,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight()
                    .padding(4.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = gameName,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = stringResource(R.string.release),
                        style = MaterialTheme.typography.labelLarge
                    )

                    Text(
                        modifier = Modifier.weight(1f),
                        text = releaseData,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun GameImage(gameUrl: String, contentDescription: String?, size: Dp) {
    val painter = rememberAsyncImagePainter(gameUrl)
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
            Box(
                modifier = Modifier
                    .size(size)
                    .background(Color.LightGray)

            )
        }

        is AsyncImagePainter.State.Success -> {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                modifier = Modifier
                    .width(size)
                    .fillMaxHeight(),
                contentScale = ContentScale.FillBounds,
            )
        }
    }
}

@Preview
@Composable
private fun GameCardComponentPreview() {
    MySpeedRunnersTheme {
        GameCardComponent(
            gameUrl = "https://www.speedrun.com/static/game/pd0qq31e/cover?v=8b6ea7d",
            gameName = "Super Mario Odyssey",
            releaseData = "2021-01-01",
            onClick = {},
        )
    }
}