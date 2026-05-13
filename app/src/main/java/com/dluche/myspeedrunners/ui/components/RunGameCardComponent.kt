package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.run.RunStatusEnum
import com.dluche.myspeedrunners.domain.model.runner.NameStyle
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.extension.RunWithNotNullNorEmpty
import com.dluche.myspeedrunners.ui.fake.run1
import com.dluche.myspeedrunners.ui.fake.runner1
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.dluche.myspeedrunners.ui.theme.bronzeColor
import com.dluche.myspeedrunners.ui.theme.goldColor
import com.dluche.myspeedrunners.ui.theme.silverColor
import com.dluche.myspeedrunners.ui.utils.getRunnerGradientColor

@Composable
fun RunGameCardComponent(
    runnerId: String,
    runnerUrl: String?,
    runnerName: String,
    runnerNameStyle: NameStyle?,
    runnerLocation: String?,
    runnerLocationUrl: String?,
    runCategory: String,
    runStatus: RunStatusEnum,
    runSubmitted: String,
    runTime: String,
    modifier: Modifier = Modifier,
    runPlace: Int? = null,
    size: Dp = 80.dp,
    onClick: () -> Unit = {},
) {

    val colorFilter by remember {
        derivedStateOf {
            when (runPlace) {
                1 -> goldColor
                2 -> silverColor
                3 -> bronzeColor
                else -> Color.Transparent
            }
        }
    }

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
            Box(modifier = Modifier.wrapContentSize(align = Alignment.TopStart)) {

                RunnerImage(runnerUrl.orEmpty(), imageSize = size, imageErrorSize = size)

                runPlace?.let {
                    Box(
                        modifier = Modifier
//                            .background(
//                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
//                                shape = CircleShape
//                            )
//                            .border(
//                                2.dp,
//                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
//                                CircleShape
//                            )
                            .wrapContentSize(align = Alignment.Center)
                            .padding(2.dp)
                    ) {
                        if (it <= 3) {
                            Image(
                                imageVector = Icons.Filled.EmojiEvents,
                                contentDescription = stringResource(R.string.rank_label),
                                modifier = Modifier.size(16.dp),
                                colorFilter = ColorFilter.tint(colorFilter)
                            )
                        } else {
                            Text(
                                text = it.toString(),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .size(16.dp)
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = runnerName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            brush = getRunnerGradientColor(
                                nameStyle = runnerNameStyle
                            )
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    runnerLocationUrl.RunWithNotNullNorEmpty { locationUrl ->
                        AsyncImage(
                            model = locationUrl,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            placeholder = painterResource(id = R.drawable.ic_map_marker_radius),
                            error = painterResource(id = R.drawable.ic_map_marker_radius)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = runCategory,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 2
                    )

                    RunStatusComponent(
                        runStatus = runStatus,
                        modifier = Modifier.wrapContentWidth(),
                        showLabel = false
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        modifier = Modifier.weight(1f),
                        text = runSubmitted,
                        style = MaterialTheme.typography.labelMedium,
                    )

                    Text(
                        modifier = Modifier.weight(1f),
                        text = runTime,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RunnerCardComponentPreview() {
    MySpeedRunnersTheme {

        val card = RunnerCard(
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
        )

        RunGameCardComponent(
            runnerId = card.id,
            runnerUrl = card.imageUrl,
            runnerName = card.name,
            runnerNameStyle = card.nameStyle,
            runnerLocation = card.location,
            runnerLocationUrl = card.locationUrl,
            runCategory = run1.category.name,
            runStatus = RunStatusEnum.NEW,
            runSubmitted = "2021-01-01",
            runTime = run1.primaryTime,
            runPlace = 1,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun RunnerCardComponentPreview2() {
    MySpeedRunnersTheme {
        val card = RunnerCard(
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
        )

        RunGameCardComponent(
            runnerId = card.id,
            runnerUrl = card.imageUrl,
            runnerName = card.name,
            runnerNameStyle = card.nameStyle,
            runnerLocation = card.location,
            runnerLocationUrl = card.locationUrl,
            runCategory = run1.category.name,
            runStatus = RunStatusEnum.VERIFIED,
            runSubmitted = "2021-01-01",
            runTime = run1.primaryTime,
            runPlace = 4,
            onClick = {}
        )
    }
}