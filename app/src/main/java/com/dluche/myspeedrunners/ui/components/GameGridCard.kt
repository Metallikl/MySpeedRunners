package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.ui.fake.game1
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable

fun GameGridCard(
    game: Game,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
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

            AsyncImage(
                model = game.imageUrl,
                contentDescription = game.name,
                modifier = Modifier
                    .size(150.dp),
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(id = R.drawable.ic_trophy_solid)
            )
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