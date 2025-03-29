package com.dluche.myspeedrunners.ui.feature.runnerdetails.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.automirrored.outlined.StarHalf
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    viewModel: RunnerDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    RunnerDetailsScreen(
        uiState = uiState.value,
        onBackClick = onBackClick,
        tryAgain = viewModel::dispatchEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunnerDetailsScreen(
    uiState: RunnerDetailsUiState,
    onBackClick: () -> Unit,
    tryAgain: () -> Unit
) {
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) { paddingValues ->
        when (uiState) {
            RunnerDetailsUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(100.dp)
                    )
                }

            }

            is RunnerDetailsUiState.Success -> {
                RunnerDetailsContent(
                    modifier = Modifier.padding(paddingValues),
                    runner = uiState.runner,
                    onBackClick = onBackClick,
                    tryAgain = tryAgain
                )
            }

            is RunnerDetailsUiState.Error -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
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
fun RunnerDetailsContent(
    modifier: Modifier = Modifier,
    runner: Runner,
    tryAgain: () -> Unit,
    onBackClick: () -> Unit
) {
    val backgroundColor = getBackgroundColor(isSystemInDarkTheme(), runner)
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(backgroundColor)
            )
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = {
                        onBackClick
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        onBackClick
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Back"
                    )
                }
            }

            AsyncImage(
                model = runner.imageUrl
                    ?: "https://www.speedrun.com/static/user/kjp1v74j/image.png?v=8db2d00",
                contentDescription = "Runner Image",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = MaterialTheme.shapes.extraLarge.copy(
                            bottomStart = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp)
                        )
                    )
                    .clip(
                        shape = MaterialTheme.shapes.extraLarge.copy(
                            bottomStart = CornerSize(0.dp),
                            bottomEnd = CornerSize(0.dp)
                        )
                    )
                    .padding(16.dp)
                ,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
fun getBackgroundColor(isDarkTheme: Boolean, runner: Runner): MutableList<Color> {
    val brushList = mutableListOf<Color>()
    runner.nameStyle?.let{ nameStyle ->
        when (nameStyle.style) {
            NameStyleEnum.GRADIENT -> {
                if (isDarkTheme) {
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
            }

            NameStyleEnum.SOLID -> {
                if (isDarkTheme) {
                    nameStyle.color?.dark?.let {
                        brushList.add(Color(it.toColorInt()))
                    }
                } else {
                    nameStyle.color?.light?.let{
                        brushList.add(Color(it.toColorInt()))
                    }
                }
            }

            else -> brushList.add(MaterialTheme.colorScheme.onBackground)
        }
    }
    if(brushList.isEmpty()){
        brushList.add(MaterialTheme.colorScheme.onBackground)
    }
    if(brushList.size == 1){
        //brushList.add(MaterialTheme.colorScheme.onBackground)
        brushList.add(
            brushList.first().copy(alpha = 0.7f)
        )
    }

    return brushList
}

@Composable
fun RunnerDetailsContents(modifier: Modifier = Modifier, runner: Runner, tryAgain: () -> Unit) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
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
            tryAgain = {},
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RunnerDetailsScreenLoadingPreview() {
    MySpeedRunnersTheme {
        RunnerDetailsScreen(
            uiState = RunnerDetailsUiState.Loading,
            tryAgain = {},
            onBackClick = {  }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RunnerDetailsScreenErrorPreview() {
    MySpeedRunnersTheme {
        RunnerDetailsScreen(
            uiState = RunnerDetailsUiState.Error("Ops, não encontramos o seu runner"),
            tryAgain = {},
            onBackClick = {}
        )
    }
}

