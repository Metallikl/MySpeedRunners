package com.dluche.myspeedrunners.ui.feature.runnerrunslist.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.game.Game
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.extension.HandleStates
import com.dluche.myspeedrunners.ui.components.GameFilterGridCard
import com.dluche.myspeedrunners.ui.components.RunCard
import com.dluche.myspeedrunners.ui.components.RunnerRunTopBar
import com.dluche.myspeedrunners.ui.components.RunnerRunTopBarSkeleton
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.uievent.RunnerRunsListEvent
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.uistate.RunnerRunsListUiState
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.uistate.RunnerRunsListUiState.RunnerState
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.viewmodel.RunnerRunsListViewModel
import com.dluche.myspeedrunners.ui.theme.MySpeedRunColors
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@ExperimentalMaterial3Api
@Composable
fun RunnerRunsListRoute(
    viewModel: RunnerRunsListViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navigateToRunDetails: (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }

    RunnerRunsListScreen(
        uiState = uiState.value,
        navigateToRunDetails = navigateToRunDetails,
        onBackClick = onBackClick,
        onFilterClick = {
            showBottomSheet = true
        },
        onClearFilterClick = {
            viewModel.dispatchEvent(RunnerRunsListEvent.ClearFilter)
        }
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            GameFilterBottomSheetContent(
                gameState = uiState.value.gamesState,
                onGameSelected = { game ->
                    viewModel.dispatchEvent(RunnerRunsListEvent.FilterByGame(game = game))
                    showBottomSheet = false
                }
            )
        }
    }
}

@Composable
fun GameFilterBottomSheetContent(
    gameState: RunnerRunsListUiState.GamesFilterState,
    onGameSelected: (Game) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (gameState) {
            RunnerRunsListUiState.GamesFilterState.Error -> {
                Text(
                    text = "Erro",
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            RunnerRunsListUiState.GamesFilterState.Loading -> {
                CircularProgressIndicator()
            }

            is RunnerRunsListUiState.GamesFilterState.Success -> {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(
                        R.string.game_filter_select_lbl
                    ),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                    ,
                    text = stringResource(
                        R.string.game_filter_total_lbl,
                        gameState.games.size
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                )

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 100.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(
                        count = gameState.games.size,
                        key = { idx -> gameState.games[idx].id }) { idx ->
                        val game = gameState.games[idx]
                        GameFilterGridCard(
                            game = game,
                            size = 100.dp,
                            onClick = {
                                onGameSelected(game)
                            }
                        )
                    }
                }
            }
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun RunnerRunsListScreen(
    uiState: RunnerRunsListUiState,
    navigateToRunDetails: (String) -> Unit,
    onBackClick: () -> Unit,
    onFilterClick: () -> Unit,
    onClearFilterClick: () -> Unit = {}
) {
    val pagingState = uiState.runs.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            handleRunnerStates(
                uiState = uiState,
                onBackClick = onBackClick,
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GameFilter(
                selectedGame = uiState.selectedGame,
                onFilterClick = onFilterClick,
                onClearFilterClick = onClearFilterClick
            )

            PaginatedRuns(pagingState, navigateToRunDetails)
        }
    }
}

@Composable
fun GameFilter(
    selectedGame: Game?,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
    onClearFilterClick: () -> Unit
) {
    val cardText = selectedGame?.name ?: stringResource(R.string.game_filter_lbl)
    Box(
        modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            onFilterClick()
                        },
                    imageVector = Icons.Filled.SportsEsports,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    modifier = Modifier
                        .weight(1f, fill = true)
                        .padding(start = 8.dp)
                        .clickable {
                            onFilterClick()
                        },
                    text = cardText,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2
                )

                selectedGame?.let {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onClearFilterClick()
                            },
                        imageVector = Icons.Filled.RemoveCircle,
                        contentDescription = null,
                        tint = MySpeedRunColors.rejectedRed
                    )
                }
            }

        }
    }

}

@Composable
private fun PaginatedRuns(
    pagingState: LazyPagingItems<Run>,
    navigateToRunDetails: (String) -> Unit
) {
    pagingState.loadState.refresh.HandleStates(
        loadingContent = {
            CircularProgressIndicator()
        },
        errorContent = {
            Text(
                text = "Algo deu errado ao carregar as corridas",
                modifier = Modifier.fillMaxSize(0.5f),
            )
        }
    ) {
        PaginatedRunList(pagingState, navigateToRunDetails)
    }
}

@ExperimentalMaterial3Api

@Composable
private fun handleRunnerStates(
    uiState: RunnerRunsListUiState,
    onBackClick: () -> Unit
) {

    when (uiState.runnerState) {
        RunnerState.Error -> {
            RunnerRunTopBarSkeleton()
        }

        RunnerState.Loading -> {
            RunnerRunTopBarSkeleton()
        }

        is RunnerState.Success -> {
            RunnerRunTopBar(
                runnerCard = uiState.runnerState.runnerCard,
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
fun PaginatedRunList(
    runList: LazyPagingItems<Run>,
    navigateToRunDetails: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            count = runList.itemCount,
            key = { idx -> runList[idx]?.id ?: idx },
        ) { idx ->
            runList[idx]?.let {
                RunCard(
                    gameUrl = it.game.imageUrl,
                    gameName = it.game.name,
                    category = it.category.name,
                    status = it.status,
                    submitted = it.date,
                    onClick = {
                        navigateToRunDetails(it.id)
                    }
                )
            }
        }
    }

}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun RunnerRunsListScreenPreview() {
    MySpeedRunnersTheme {
//        RunnerRunsListScreen(
//            RunnerRunsListUiState(),
//            navigateToRunDetails = {},
//            onBackClick = {}
//        )
    }
}
