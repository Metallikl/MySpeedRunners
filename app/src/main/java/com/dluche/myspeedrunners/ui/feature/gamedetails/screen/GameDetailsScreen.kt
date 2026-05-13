package com.dluche.myspeedrunners.ui.feature.gamedetails.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.extension.RunWithNotNullNorEmpty
import com.dluche.myspeedrunners.ui.components.BackgroundImageComponent
import com.dluche.myspeedrunners.ui.components.GameCoverComponent
import com.dluche.myspeedrunners.ui.components.GameCoverComponentV2
import com.dluche.myspeedrunners.ui.components.GenericErrorWithButtonComponent
import com.dluche.myspeedrunners.ui.components.LeaderboardRunsContainerComponent
import com.dluche.myspeedrunners.ui.components.SelectableFlowRowContainer
import com.dluche.myspeedrunners.ui.components.RunnerCardComponent
import com.dluche.myspeedrunners.ui.components.RunsGameContainerComponent
import com.dluche.myspeedrunners.ui.components.RunsSkeletonList
import com.dluche.myspeedrunners.ui.components.mapToSelectableFlowRowData
import com.dluche.myspeedrunners.ui.feature.gamedetails.model.GameDetailTabItem
import com.dluche.myspeedrunners.ui.feature.gamedetails.model.GameDetailTabType
import com.dluche.myspeedrunners.ui.feature.gamedetails.model.GameDetailsBottomSheetType
import com.dluche.myspeedrunners.ui.feature.gamedetails.model.GameDetailsTabFactory
import com.dluche.myspeedrunners.ui.feature.gamedetails.uievents.GameDetailsEvents
import com.dluche.myspeedrunners.ui.feature.gamedetails.uistate.GameDetailsUiState
import com.dluche.myspeedrunners.ui.feature.gamedetails.uistate.GameDetailsUiState.LeaderboardState
import com.dluche.myspeedrunners.ui.feature.gamedetails.uistate.GameDetailsUiState.MainState
import com.dluche.myspeedrunners.ui.feature.gamedetails.uistate.GameDetailsUiState.RunsState
import com.dluche.myspeedrunners.ui.feature.gamedetails.viewmodel.GameDetailsViewModel
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch
import kotlin.String

@ExperimentalMaterial3Api
@Composable
fun GameDetailsRoute(
    navigateToRunnerDetails: (String) -> Unit,
    navigateToRunDetails: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<GameDetailsViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var bottomSheetType by remember { mutableStateOf(GameDetailsBottomSheetType.NONE) }
    val tabList = remember {
        GameDetailsTabFactory.getTabList(context)
    }

    GameDetailsScreen(
        uiState = uiState.value,
        onDispatchEvent = { viewModel.dispatchEvent(it) },
        onBackClick = onBackClick,
        navigateToRunnerDetails = navigateToRunnerDetails,
        onChangeBottomSheetType = {
            bottomSheetType = it
        },
        gameDetailTabItems = tabList,
        navigateToRunDetails = navigateToRunDetails
    )

    LaunchedEffect(Unit) {
        viewModel.dispatchEvent(GameDetailsEvents.LoadGameDetails)
    }

    when (bottomSheetType) {
        GameDetailsBottomSheetType.PLATFORM, GameDetailsBottomSheetType.CATEGORY -> {
            if (uiState.value.mainState is MainState.Success) {
                ModalBottomSheet(
                    onDismissRequest = { bottomSheetType = GameDetailsBottomSheetType.NONE },
                    sheetState = sheetState
                ) {
                    if (bottomSheetType == GameDetailsBottomSheetType.PLATFORM) {
                        SelectableFlowRowContainer(
                            label = stringResource(R.string.platforms_label),
                            data = (uiState.value.mainState as MainState.Success).game.platforms.mapToSelectableFlowRowData { mapper ->
                                mapper(id, name)
                            },
//                            onItemClick = {
//                                Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
//                            }
                        )
                    } else {
                        SelectableFlowRowContainer(
                            label = stringResource(R.string.category_label),
                            data = (uiState.value.mainState as MainState.Success).game.categories.mapToSelectableFlowRowData { mapper ->
                                mapper(id, name)
                            },
                            onItemClick = {
                                Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }

        GameDetailsBottomSheetType.NONE -> {}
    }
}

@Composable
fun GameDetailsScreen(
    uiState: GameDetailsUiState = GameDetailsUiState(),
    onDispatchEvent: (GameDetailsEvents) -> Unit = {},
    onBackClick: () -> Unit = {},
    navigateToRunnerDetails: (String) -> Unit,
    onChangeBottomSheetType: (GameDetailsBottomSheetType) -> Unit = {},
    gameDetailTabItems: List<GameDetailTabItem>,
    navigateToRunDetails: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center

    ) {
        BackgroundComponent(uiState)

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                //todo extrair como topBar para reuso
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0f))
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = {
                        onBackClick()
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

//                IconButton(
//                    onClick = {
//                        onDispatchEvent(GameDetailsEvents.LoadGameDetails)
//                    },
//                    colors = IconButtonDefaults.iconButtonColors(
//                        contentColor = MaterialTheme.colorScheme.onBackground
//                    )
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.FavoriteBorder,
//                        contentDescription = "Back"
//                    )
//                }

            }
            // val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    //.verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (uiState !is MainState.Error) {

                    GameNameComponent(uiState.mainState)

                    GameCover(uiState.mainState, onChangeBottomSheetType)

                    ContentComponent(uiState, navigateToRunnerDetails, gameDetailTabItems,navigateToRunDetails)

                } else {
                    GenericErrorWithButtonComponent(
                        modifier = Modifier.fillMaxSize(),
                        onRetry = {
                            onDispatchEvent(GameDetailsEvents.LoadGameDetails)
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun GameCover(
    uiState: MainState,
    onChangeBottomSheetType: (GameDetailsBottomSheetType) -> Unit
) {
    when (uiState) {
        MainState.Loading -> {
            GameCoverComponent(
                imageUrl = "",
                isLoading = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        is MainState.Success -> {
            GameCoverComponentV2(
                name = uiState.game.name,
                releaseDate = uiState.game.releaseData,
                imageUrl = uiState.game.imageUrl,
                isLoading = false,
                modifier = Modifier
                    .fillMaxWidth(),
                onPlatformClick = {
                    onChangeBottomSheetType(GameDetailsBottomSheetType.PLATFORM)
                },
                onCategoryClick = {
                    onChangeBottomSheetType(GameDetailsBottomSheetType.CATEGORY)
                },
                discordUrl = uiState.game.discord
            )
        }

        is MainState.Error -> {}
    }
}

@Composable
fun BackgroundComponent(state: GameDetailsUiState) {
    when (state) {
        MainState.Loading -> {
            Box(
                modifier = Modifier
                    .shimmer()
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )
        }

        is MainState.Error -> {}
        is MainState.Success -> {
            BackgroundImageComponent(
                backgroundUrl = state.game.backgroundUrl,
            )
        }
    }
}

@Composable
fun GameNameComponent(uiState: MainState, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {

        when (uiState) {
            is MainState.Error -> {
            }

            MainState.Loading -> {
                Box(
                    modifier = Modifier
                        .shimmer()
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .height(32.dp)
                        .background(Color.Gray)
                )
            }

            is MainState.Success -> {
                Text(
                    text = uiState.game.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun ContentComponent(
    uiState: GameDetailsUiState,
    navigateToRunnerDetails: (String) -> Unit,
    gameDetailTabItems: List<GameDetailTabItem>,
    navigateToRunDetails: (String) -> Unit
) {
    when (uiState.mainState) {
        is MainState.Success -> {
            val pagerState = rememberPagerState(pageCount = { gameDetailTabItems.size })
            val scope = rememberCoroutineScope()

            TabRow(
                modifier = Modifier.clip(CircleShape),
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f)
            ) {
                gameDetailTabItems.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = index == pagerState.currentPage,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
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
                    .fillMaxHeight()
            ) {
                when (gameDetailTabItems[pagerState.currentPage].tabType) {
                    GameDetailTabType.RUNS -> {
                        RunsContainer(uiState.runsState, navigateToRunDetails)
                    }

                    GameDetailTabType.LEADERBOARD -> {
                        LeaderboardContainer(uiState.leaderboardState,navigateToRunDetails)
                    }

                    GameDetailTabType.RECORDS -> {
                        ModeratorsContainer(uiState.mainState, navigateToRunnerDetails)
                    }

                    GameDetailTabType.MODERATORS -> {
                        ModeratorsContainer(uiState.mainState, navigateToRunnerDetails)
                    }
                }
            }
        }

        else -> {
            Box(
                modifier = Modifier
                    .shimmer()
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(400.dp)
                    .background(Color.Gray)
            )
        }
    }
}

@Composable
private fun LeaderboardContainer(
    leaderboardState: LeaderboardState,
    navigateToRunDetail: (String) -> Unit,
    onRetry: () -> Unit = {},
    onShowMoreClick: () -> Unit = {}
) {
    when (leaderboardState) {
        is LeaderboardState.Error -> {
            GenericErrorWithButtonComponent(
                onRetry = onRetry,
                modifier = Modifier.fillMaxSize(),
                interaction = 1
            )
        }

        LeaderboardState.Loading -> {
            RunsSkeletonList()
        }

        is LeaderboardState.Success -> {
            LeaderboardRunsContainerComponent(
                runs = leaderboardState.runs,
                onNavigateToRunDetails = navigateToRunDetail,
                onShowMoreClick = onShowMoreClick,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun RunsContainer(
    runState: RunsState,
    navigateToRunDetail: (String) -> Unit,
    onRetry: () -> Unit = {},
    onShowMoreClick: () -> Unit = {}
) {
    when (runState) {
        is RunsState.Error -> {
            GenericErrorWithButtonComponent(
                onRetry = onRetry,
                modifier = Modifier.fillMaxSize(),
                interaction = 1
            )
        }

        RunsState.Loading -> {
            RunsSkeletonList()
        }

        is RunsState.Success -> {
            RunsGameContainerComponent(
                runs = runState.runs,
                onNavigateToRunDetails = navigateToRunDetail,
                onShowMoreClick = onShowMoreClick,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun ModeratorsContainer(
    uiState: MainState.Success,
    navigateToRunnerDetails: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        uiState.game.moderators.RunWithNotNullNorEmpty { runners ->
            if (runners is List) {
                LazyColumn {
                    item {
                        Text(
                            text = stringResource(R.string.moderators),
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier
                                .wrapContentWidth(),
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = Bold
                        )
                    }
                    items(
                        count = runners.size,
                        key = { idx -> runners[idx].id }
                    ) { idx ->
                        val runner = runners[idx]
                        RunnerCardComponent(
                            runnerCard = runner,
                            onClick = { navigateToRunnerDetails(runner.id) }
                        )
                    }
                }
            }

//
//
//            runners.forEach { runner ->
//                RunnerCardComponent(
//                    runnerCard = runner,
//                    onClick = { navigateToRunnerDetails(runner.id) }
//
//                )
//            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun PlatformContainer(uiState: MainState.Success) {
    uiState.game.platforms.RunWithNotNullNorEmpty { platforms ->
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.platforms_label),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = Bold
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                platforms.forEach { platform ->
                    OutlinedCard {
                        Text(
                            text = platform.name,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(8.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryContainer(uiState: MainState.Success) {

    uiState.game.categories.RunWithNotNullNorEmpty { categories ->
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.category_label),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = Bold
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    OutlinedCard {
                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(8.dp),
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun GameDetailsScreenPreview() {
    MySpeedRunnersTheme {
        GameDetailsScreen(navigateToRunnerDetails = { }, navigateToRunDetails = {},gameDetailTabItems = emptyList())
    }
}
