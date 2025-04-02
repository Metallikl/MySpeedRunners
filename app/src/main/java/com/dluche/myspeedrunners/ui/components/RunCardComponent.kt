package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable

fun RunCard(
    gameUrl: String,
    gameName: String,
    category: String,
    status: String,
    submitted: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable{
                onClick()
            }

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = gameUrl,
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(100.dp),
                contentScale = ContentScale.FillBounds
                    
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(4.dp)
                ,
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
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = status,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = submitted,
                        style = MaterialTheme.typography.labelMedium
                    )
                }

            }
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
            status = "Verified",
            submitted = "2021-01-01",
            onClick = {},
        )
    }
}