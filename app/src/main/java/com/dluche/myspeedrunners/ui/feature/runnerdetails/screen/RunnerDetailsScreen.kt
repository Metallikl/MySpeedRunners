package com.dluche.myspeedrunners.ui.feature.runnerdetails.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.DirectionsRun
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.VideogameAsset
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.domain.model.runner.NameStyle
import com.dluche.myspeedrunners.domain.model.runner.NameStyleEnum
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.extension.getTranslation
import com.dluche.myspeedrunners.ui.components.InfoContent
import com.dluche.myspeedrunners.ui.components.RunCard
import com.dluche.myspeedrunners.ui.fake.runner1
import com.dluche.myspeedrunners.ui.feature.runnerdetails.model.RunnerDetailsTabItem
import com.dluche.myspeedrunners.ui.feature.runnerdetails.model.RunnerDetailsTabType
import com.dluche.myspeedrunners.ui.feature.runnerdetails.uistate.RunnerDetailsUiState
import com.dluche.myspeedrunners.ui.feature.runnerdetails.uistate.RunnerDetailsUiState.HeaderState.Loading
import com.dluche.myspeedrunners.ui.feature.runnerdetails.uistate.RunnerDetailsUiState.HeaderState.Success
import com.dluche.myspeedrunners.ui.feature.runnerdetails.viewmodel.RunnerDetailsViewModel
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import kotlinx.coroutines.launch

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
        when (uiState.headerState) {
            Loading -> {
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

            is Success -> {
                RunnerDetailsContent(
                    modifier = Modifier.padding(paddingValues),
                    runner = uiState.headerState.runner,
                    runsState = uiState.runsState,
                    onBackClick = onBackClick,
                    tryAgain = tryAgain
                )
            }

            is RunnerDetailsUiState.HeaderState.Error -> {
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.headerState.message,
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
    runsState: RunnerDetailsUiState.RunsState,
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
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                        tryAgain()
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
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = runner.name,
                    style = getRunnerNameTextStyle(runner.nameStyle),
                )

                val tabItems = getTabList()
               // var selectedTabIndex by remember { mutableStateOf(0) }
                val pagerState = rememberPagerState(pageCount = { tabItems.size })
                val scope = rememberCoroutineScope()

//                LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
//                    if (pagerState.currentPage == pagerState.targetPage) {
//                        selectedTabIndex = pagerState.currentPage
//                    }
//                }

                TabRow(selectedTabIndex = pagerState.currentPage) {
                    tabItems.forEachIndexed { index, tabItem ->
                        Tab(
                            selected = index == pagerState.currentPage,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
//                            text = {
//                                Text(
//                                    text = tabItem.title,
//                                    fontSize = 10.sp,
//                                    color = MaterialTheme.colorScheme.onBackground
//                                )
//                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == pagerState.currentPage) tabItem.selectedIcon else tabItem.unselectedIcon,
                                    contentDescription = tabItem.title,
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    when (tabItems[pagerState.currentPage].tabType) {
                        RunnerDetailsTabType.SOCIAL_NETWORK -> {
                            InfoContent(runner = runner)
                        }

                        RunnerDetailsTabType.RUNS -> {
                            RunsStateHandler(runsState)
                        }

                        RunnerDetailsTabType.GAMES -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Games",
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RunsStateHandler(runsState: RunnerDetailsUiState.RunsState) {
    when (runsState) {
        is RunnerDetailsUiState.RunsState.Error -> {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                )
            }
        }

        RunnerDetailsUiState.RunsState.Loading -> {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(100.dp)
                )
            }
        }

        is RunnerDetailsUiState.RunsState.Success -> RunsContainer(runsState.runs)
    }

}

@Composable
private fun RunsContainer(runs: List<Run>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(runs) {
            RunCard(
                gameUrl = it.game.imageUrl,
                gameName = it.game.name,
                category = it.category.name,
                status = it.status.getTranslation(),
                submitted = it.date,
                onClick = {

                }
            )
        }
    }
}


@Composable
fun getBackgroundColor(isDarkTheme: Boolean, runner: Runner): MutableList<Color> {
    val brushList = mutableListOf<Color>()
    runner.nameStyle?.let { nameStyle ->
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
                    nameStyle.color?.light?.let {
                        brushList.add(Color(it.toColorInt()))
                    }
                }
            }

            else -> brushList.add(MaterialTheme.colorScheme.onBackground)
        }
    }
    if (brushList.isEmpty()) {
        brushList.add(MaterialTheme.colorScheme.onBackground)
    }
    if (brushList.size == 1) {
        //brushList.add(MaterialTheme.colorScheme.onBackground)
        brushList.add(
            brushList.first().copy(alpha = 0.7f)
        )
    }

    return brushList
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

@Composable
private fun getTabList() = listOf(
    RunnerDetailsTabItem(
        tabType = RunnerDetailsTabType.RUNS,
        title = stringResource(R.string.runs_tab_label),
        selectedIcon = Icons.AutoMirrored.Filled.DirectionsRun,
        unselectedIcon = Icons.AutoMirrored.Outlined.DirectionsRun
    ),
    RunnerDetailsTabItem(
        tabType = RunnerDetailsTabType.GAMES,
        title = stringResource(R.string.games_tab_label),
        selectedIcon = Icons.Filled.VideogameAsset,
        unselectedIcon = Icons.Outlined.VideogameAsset
    ),
    RunnerDetailsTabItem(
        tabType = RunnerDetailsTabType.SOCIAL_NETWORK,
        title = stringResource(R.string.info_tab_label),
        selectedIcon = Icons.Default.Info,
        unselectedIcon = Icons.Outlined.Info
    ),
)

@Preview(showBackground = true)
@Composable
private fun RunnerDetailsScreenSuccessPreview() {
    MySpeedRunnersTheme {
        RunnerDetailsScreen(
            uiState = RunnerDetailsUiState(
                headerState = Success(
                    runner1
                ),
                runsState = RunnerDetailsUiState.RunsState.Loading
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
            uiState = RunnerDetailsUiState(),
            tryAgain = {},
            onBackClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RunnerDetailsScreenErrorPreview() {
    MySpeedRunnersTheme {
        RunnerDetailsScreen(
            uiState = RunnerDetailsUiState(headerState = RunnerDetailsUiState.HeaderState.Error("Ops, não encontramos o seu runner")),
            tryAgain = {},
            onBackClick = {}
        )
    }
}

