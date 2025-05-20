package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.extension.RunWithNotNullNorEmpty
import com.dluche.myspeedrunners.ui.fake.runner1
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.dluche.myspeedrunners.ui.utils.getRunnerGradientColor
import com.valentinilk.shimmer.shimmer

@Composable
fun RunnerCardComponent(
    runnerCard: RunnerCard,
    modifier: Modifier = Modifier,
    size: Dp = 80.dp,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            RunnerImage(runnerCard, size)

            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = runnerCard.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        brush = getRunnerGradientColor(
                            nameStyle = runnerCard.nameStyle
                        )
                    ),
                )

                runnerCard.pronouns?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                runnerCard.location.RunWithNotNullNorEmpty { location->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        runnerCard.locationUrl?.let{
                            AsyncImage(
                                model = it ,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                placeholder = painterResource(id = R.drawable.ic_map_marker_radius),
                                error = painterResource(id = R.drawable.ic_map_marker_radius)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            modifier = Modifier.weight(1f),
                            text = location,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun RunnerImage(runnerCard: RunnerCard, size: Dp) {
    Box(
        modifier = Modifier
            .size(size),
        contentAlignment = Alignment.Center
    ) {

        val painter = rememberAsyncImagePainter(runnerCard.imageUrl)
        val state = painter.state.collectAsState()

        when (state.value) {
            AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .shimmer()
                        .size(size)
                        .clip(CircleShape)
                        .background(Color.LightGray)

                )
            }

            is AsyncImagePainter.State.Error -> {
                //todo tratar o cenario de erro
                Image(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "Error loading image",
                    modifier = Modifier
                        .size(size),
                    colorFilter = ColorFilter.tint(
                        MaterialTheme.colorScheme.onSurface
                    )

                )
            }

            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(size)
                        .clip(CircleShape),
                    contentScale = ContentScale.FillBounds,
                )
            }
        }
    }
}

@Preview
@Composable
private fun RunnerCardComponentPreview() {
    MySpeedRunnersTheme {
        RunnerCardComponent(
            runnerCard = RunnerCard(
                id = runner1.id,
                name = runner1.name,
                imageUrl = runner1.imageUrl,
                pronouns = runner1.pronouns,
                japaneseName = null,
                location = runner1.location,
                locationUrl = runner1.locationUrl,
                nameStyle = runner1.nameStyle,
                role = runner1.role,
                signup = runner1.signup,
            ),
            onClick = {}
        )
    }
}