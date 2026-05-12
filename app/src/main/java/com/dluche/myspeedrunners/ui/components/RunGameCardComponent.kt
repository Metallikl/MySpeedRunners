package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
    size: Dp = 80.dp,
    onClick: () -> Unit = {},
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

            RunnerImage(runnerUrl.orEmpty(), imageSize = size, imageErrorSize = size)

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
                        text = runCategory,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.labelLarge,
                        maxLines = 2
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
            onClick = {}
        )
    }
}