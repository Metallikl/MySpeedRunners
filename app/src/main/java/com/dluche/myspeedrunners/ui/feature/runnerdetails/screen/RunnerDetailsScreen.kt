package com.dluche.myspeedrunners.ui.feature.runnerdetails.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.dluche.myspeedrunners.data.datasource.model.RunnerDto
import com.dluche.myspeedrunners.ui.feature.runnerdetails.viewmodel.RunnerDetailsViewModel
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun RunnerDetailsRoute(
    viewModel: RunnerDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.runnerState.collectAsStateWithLifecycle()
    RunnerDetailsScreen(uiState.value)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunnerDetailsScreen(
    uiState: RunnerDto?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Runner Details")
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                )

            )
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) { paddingValues ->
        RunnerDetailsContent(Modifier.padding(paddingValues), uiState)
    }

}

@Composable
fun RunnerDetailsContent(modifier: Modifier = Modifier, uiState: RunnerDto?) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://www.speedrun.com/static/user/kjp1v74j/image.png?v=8db2d00",
                contentDescription = "Runner Image",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(150.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "LD_SPEEDRUNS",
                    style = TextStyle(
                        brush =
                            Brush.horizontalGradient(
                                listOf(
                                    Color("#A259C5".toColorInt()),
                                    Color("#EE2222".toColorInt())
                                )
                            ),
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        fontWeight = MaterialTheme.typography.headlineSmall.fontWeight
                    ),
                )

                Text(
                    text = "He/ Him",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Minas Gerais, Brazil",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RunnerDetailsScreenPreview() {
    MySpeedRunnersTheme {
        RunnerDetailsScreen(
            uiState = null
        )
    }
}
