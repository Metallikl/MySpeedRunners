package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.leaderboard.LeaderboardRun
import com.dluche.myspeedrunners.extension.RunWithNotNullNorEmpty

@Composable
fun LeaderboardRunsContainerComponent(
    runs: List<LeaderboardRun>,
    onNavigateToRunDetails: (String) -> Unit,
    onShowMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    runsLimitSize: Int = 20
) {
    val showMoreButton = remember {
        runs.size >= runsLimitSize
    }

    if (runs.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item {
                label.RunWithNotNullNorEmpty { it ->
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .wrapContentWidth(),
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = Bold
                    )
                }
            }

            items(
                count = runs.size,
                key = { idx -> runs[idx].id }
            ) {
                val run = runs[it]
                RunGameCardComponent(
                    runnerId = run.runner.id,
                    runnerUrl = run.runner.imageUrl,
                    runnerName = run.runner.name,
                    runnerNameStyle = run.runner.nameStyle,
                    runnerLocation = run.runner.location,
                    runnerLocationUrl = run.runner.locationUrl,
                    runPlace = run.place,
                    runCategory = run.category.name,
                    runStatus = run.status,
                    runSubmitted = run.date,
                    runTime = run.primaryTime,
                    onClick = {
                        onNavigateToRunDetails(run.id)
                    }
                )

            }
            if (showMoreButton) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        OutlinedButton(
                            modifier = Modifier.fillMaxWidth(0.8f),
                            onClick = {
                                onShowMoreClick()
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.see_more_lable)
                            )
                        }
                    }
                }
            }
        }
    } else {
        EmptyListComponent(stringResource(R.string.no_runs_found_label))
    }
}

@Composable
private fun EmptyListComponent(label: String) {
    Box(
        modifier =
            Modifier
                .fillMaxSize(),
        contentAlignment = Alignment.Center


    ) {
        Text(
            text = label,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}