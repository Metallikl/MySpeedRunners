package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Style
import androidx.compose.material.icons.outlined.VideogameAsset
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.extension.HandleState
import com.dluche.myspeedrunners.extension.RunWithNotNullNorEmpty
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun GameCoverComponentV2(
    name: String,
    releaseDate: String,
    imageUrl: String,
    isLoading: Boolean,
    discordUrl: String,
    modifier: Modifier = Modifier,
    onPlatformClick: () -> Unit = {},
    onCategoryClick: () -> Unit = {},
    contentScale: ContentScale = ContentScale.FillBounds,
    aspectRatio: Float = 9 / 16f
) {
    val releaseYear = remember {
        releaseDate.split("/").let{
            it[it.lastIndex]
        }
    }
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
                    SuccessContent(
                        coverPainter = coverPainter,
                        aspectRatio = aspectRatio,
                        contentScale = contentScale,
                        name = name,
                        releaseDate = releaseYear,
                        onPlatformClick = onPlatformClick,
                        onCategoryClick = onCategoryClick,
                        discordUrl = discordUrl
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

@Composable
private fun SuccessContent(
    coverPainter: Painter,
    aspectRatio: Float,
    contentScale: ContentScale,
    name: String,
    releaseDate: String,
    onPlatformClick: () -> Unit = {},
    onCategoryClick: () -> Unit = {},
    discordUrl: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        Image(
            painter = coverPainter,
            contentDescription = null,
            modifier = Modifier
                .width(100.dp)
                .height(150.dp)
                .clip(MaterialTheme.shapes.medium),
            //.aspectRatio(aspectRatio)

            contentScale = contentScale,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
        ) {
            OpenOutlinedCard(
                imageVector = Icons.Outlined.VideogameAsset,
                label = stringResource(R.string.platforms_label),
                onClick = onPlatformClick
            )

            Spacer(modifier = Modifier.height(4.dp))

            OpenOutlinedCard(
                imageVector = Icons.Outlined.Style,
                label = stringResource(R.string.categories),
                onClick = onCategoryClick
            )

            discordUrl.RunWithNotNullNorEmpty {
                Spacer(modifier = Modifier.height(4.dp))
                DiscordComponent(discordUrl = it)
            }

            Spacer(modifier = Modifier.height(4.dp))

            OpenOutlinedCard(null,releaseDate)
        }
    }
}

@Composable
private fun OpenOutlinedCard(
    imageVector: ImageVector?,
    label: String,
    onClick: () -> Unit = {},
    contentDescription: String? = null
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.Start)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            imageVector?.let {
                Image(
                    imageVector = it,
                    contentDescription = contentDescription ?: label,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurfaceVariant),
                )

                Spacer(modifier = Modifier.width(8.dp))

            }

            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .wrapContentWidth(),
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GameSuccessContentPreview() {
    MySpeedRunnersTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            SuccessContent(
                name = "Resident Evil: Requiem",
                releaseDate = "2023-01-01",
                coverPainter = ColorPainter(Color.Blue),
                contentScale = ContentScale.FillBounds,
                aspectRatio = 9 / 16f,
                discordUrl = "https://www.speedrun.com/static/game/n268x51p/cover?v=5ed1e37"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GameCoverComponentPreview() {
    MySpeedRunnersTheme {
        GameCoverComponentV2(
            name = "Resident Evil: Requiem",
            releaseDate = "2023-01-01",
            imageUrl = "https://www.speedrun.com/static/game/n268x51p/cover?v=5ed1e37",
            isLoading = false,
            modifier = Modifier.fillMaxWidth(),
            discordUrl = ""
        )
    }
}

