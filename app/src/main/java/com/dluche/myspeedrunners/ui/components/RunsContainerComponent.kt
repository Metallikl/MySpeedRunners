package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.run.Run

@Composable
fun RunsContainerComponent(
    runs: List<Run>,
    onNavigateToRunDetails: (String) -> Unit,
    onShowMoreClick: () -> Unit,
) {
    val runLimitSize = 20
    if (runs.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(runs) {
                RunCard(
                    gameUrl = it.game.imageUrl,
                    gameName = it.game.name,
                    category = it.category.name,
                    status = it.status,
                    submitted = it.date,
                    onClick = {
                        onNavigateToRunDetails(it.id)
                    }
                )
            }
            if (runs.size >= runLimitSize) {
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