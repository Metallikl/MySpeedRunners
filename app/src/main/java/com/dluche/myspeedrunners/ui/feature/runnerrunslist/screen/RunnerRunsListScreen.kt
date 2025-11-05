package com.dluche.myspeedrunners.ui.feature.runnerrunslist.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.extension.HandleStates
import com.dluche.myspeedrunners.ui.components.RunCard
import com.dluche.myspeedrunners.ui.components.RunnerRunTopBar
import com.dluche.myspeedrunners.ui.components.RunnerRunTopBarSkeleton
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.uistate.RunnerRunsListUiState
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.uistate.RunnerRunsListUiState.RunnerState
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.viewmodel.RunnerRunsListViewModel
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
        }
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun RunnerRunsListScreen(
    uiState: RunnerRunsListUiState,
    navigateToRunDetails: (String) -> Unit,
    onBackClick: () -> Unit,
    onFilterClick: () -> Unit
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GameFilter(onFilterClick = onFilterClick)

            PaginatedRuns(pagingState, navigateToRunDetails)
        }
    }
}

@Composable
fun GameFilter(
    modifier: Modifier = Modifier,
    onFilterClick: () -> Unit
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(8.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clickable {
                    onFilterClick()
                },

            ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {

                Text(
                    modifier = Modifier
                        .padding(start = 8.dp),
                    text = stringResource(R.string.game_filter_lbl),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    imageVector = Icons.Filled.SportsEsports,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )

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
