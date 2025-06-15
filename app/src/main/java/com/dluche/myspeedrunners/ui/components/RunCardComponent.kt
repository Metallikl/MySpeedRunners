package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.dluche.myspeedrunners.domain.model.run.RunStatusEnum
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun RunCard(
    gameUrl: String,
    gameName: String,
    category: String,
    status: RunStatusEnum,
    submitted: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            RunImage(
                gameUrl = gameUrl,
                contentDescription = contentDescription,
                size = 100.dp
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = gameName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = category,
                    style = MaterialTheme.typography.labelLarge
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RunStatusComponent(
                        runStatus = status,
                        modifier = Modifier.wrapContentWidth()
                    )

                    Text(
                        modifier = Modifier.weight(1f),
                        text = submitted,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun RunImage(gameUrl: String, contentDescription: String?, size: Dp) {
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
                    .size(size),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Preview
@Composable
private fun RunCardPreview() {
    MySpeedRunnersTheme {
        RunCard(
            gameUrl = "https://www.speedrun.com/static/game/pd0qq31e/cover?v=8b6ea7d",
            gameName = "Super Mario Odyssey",
            category = "Any%",
            status = RunStatusEnum.VERIFIED,
            submitted = "2021-01-01",
            onClick = {},
        )
    }
}