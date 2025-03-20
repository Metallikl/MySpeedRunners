package com.dluche.myspeedrunners.ui.feature.runnerdetails.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.dluche.myspeedrunners.domain.model.runner.ColorTheme
import com.dluche.myspeedrunners.domain.model.runner.NameStyle
import com.dluche.myspeedrunners.domain.model.runner.NameStyleEnum
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.ui.feature.runnerdetails.uistate.RunnerDetailsUiState
import com.dluche.myspeedrunners.ui.feature.runnerdetails.viewmodel.RunnerDetailsViewModel
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun RunnerDetailsRoute(
    viewModel: RunnerDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    RunnerDetailsScreen(
        uiState.value, viewModel::dispatchEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunnerDetailsScreen(
    uiState: RunnerDetailsUiState,
    tryAgain: () -> Unit
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
        when (uiState) {
            RunnerDetailsUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(100.dp)
                    )
                }

            }

            is RunnerDetailsUiState.Success -> {
                RunnerDetailsContent(Modifier.padding(paddingValues), uiState.runner,tryAgain)
            }

            is RunnerDetailsUiState.Error -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        }
    }
}

@Composable
fun RunnerDetailsContent(modifier: Modifier = Modifier, runner: Runner, tryAgain: () -> Unit) {
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
                model = runner.imageUrl
                    ?: "https://www.speedrun.com/static/user/kjp1v74j/image.png?v=8db2d00",
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
                    text = runner.name,
                    style = getRunnerNameTextStyle(runner.nameStyle),
                )

                runner.pronouns?.let {
                    Text(
                        text = "He/ Him",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                when {
                    runner.region != null -> {
                        Text(
                            text = runner.region,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    runner.country != null -> {
                        Text(
                            text = runner.country,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Button(onClick = tryAgain) {
                    Text(text = "Tente outra vez")
                }
            }
        }
    }
}

@Composable
private fun getRunnerNameTextStyle(nameStyle: NameStyle?): TextStyle {
    val defaultTextStyle = TextStyle(
        color = MaterialTheme.colorScheme.onBackground,
        fontSize = MaterialTheme.typography.headlineSmall.fontSize,
        fontWeight = MaterialTheme.typography.headlineSmall.fontWeight
    )
    return if (nameStyle != null) {
        when (nameStyle.style) {
            NameStyleEnum.GRADIENT -> {
                val brushList = mutableListOf<Color>()
                if (isSystemInDarkTheme()) {
                    if (nameStyle.colorFrom?.dark != null && nameStyle.colorTo?.dark != null) {
                        brushList.add(Color(nameStyle.colorFrom.dark.toColorInt()))
                        brushList.add(Color(nameStyle.colorTo.dark.toColorInt()))
                    } else {
                        brushList.add(MaterialTheme.colorScheme.onBackground)
                    }
                } else {
                    if (nameStyle.colorFrom?.light != null && nameStyle.colorTo?.light != null) {
                        brushList.add(Color(nameStyle.colorFrom.light.toColorInt()))
                        brushList.add(Color(nameStyle.colorTo.light.toColorInt()))
                    } else {
                        brushList.add(MaterialTheme.colorScheme.onBackground)
                    }
                }

                TextStyle(
                    brush =
                        Brush.horizontalGradient(brushList),
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = MaterialTheme.typography.headlineSmall.fontWeight
                )
            }

            NameStyleEnum.SOLID -> {
                val rawColor = if (isSystemInDarkTheme()) {
                    nameStyle.color?.dark?.toColorInt()
                } else {
                    nameStyle.color?.light?.toColorInt()
                }
                TextStyle(
                    color = rawColor?.let { Color(it) }
                        ?: MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = MaterialTheme.typography.headlineSmall.fontWeight
                )
            }

            else -> defaultTextStyle
        }
    } else {
        defaultTextStyle
    }
}


@Preview(showBackground = true)
@Composable
private fun RunnerDetailsScreenLoadingPreview() {
    MySpeedRunnersTheme {
        RunnerDetailsScreen(
            uiState = RunnerDetailsUiState.Loading,
            tryAgain = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RunnerDetailsScreenErrorPreview() {
    MySpeedRunnersTheme {
        RunnerDetailsScreen(
            uiState = RunnerDetailsUiState.Error("Ops, não encontramos o seu runner"),
            tryAgain = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RunnerDetailsScreenSuccessPreview() {
    MySpeedRunnersTheme {
        RunnerDetailsScreen(
            uiState = RunnerDetailsUiState.Success(
                Runner(
                    id = "kjp1v74j",
                    name = "Daniel Luche",
                    pronouns = "He/ Him",
                    japaneseName = "ダニエル・ルシェ",
                    country = "Brazil",
                    region = "São Paulo",
                    iconUrl = "https://www.speedrun.com/static/user/kjp1v74j/icon.png?v=8db2d00",
                    imageUrl = "https://www.speedrun.com/static/user/kjp1v74j/image.png?v=8db2d00",
                    webLink = "https://www.speedrun.com/user/DanielLuche",
                    socialNetworks = listOf(),
                    nameStyle = NameStyle(
                        style = NameStyleEnum.SOLID,
                        color = ColorTheme(
                            light = "#FF0000",
                            dark = "#00FF00"
                        ),
                        colorFrom = null,
                        colorTo = null
                    ),
                    links = listOf()
                )
            ),
            tryAgain = {}
        )
    }
}
