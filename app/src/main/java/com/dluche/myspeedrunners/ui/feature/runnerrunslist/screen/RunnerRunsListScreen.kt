package com.dluche.myspeedrunners.ui.feature.runnerrunslist.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.extension.HandleStates
import com.dluche.myspeedrunners.ui.components.RunCard
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.uistate.RunnerRunsListUiState
import com.dluche.myspeedrunners.ui.feature.runnerrunslist.viewmodel.RunnerRunsListViewModel
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun RunnerRunsListRoute(
    viewModel: RunnerRunsListViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    navigateToRunDetails: (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    RunnerRunsListScreen(
        uiState = uiState.value,
        navigateToRunDetails = navigateToRunDetails,
        onBackClick = onBackClick
    )
}

@Composable
fun RunnerRunsListScreen(
    uiState: RunnerRunsListUiState,
    navigateToRunDetails: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val pagingState = uiState.runs.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
}

@Composable
fun PaginatedRunList(
    runList: LazyPagingItems<Run>,
    navigateToRunDetails: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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

@Preview
@Composable
private fun RunnerRunsListScreenPreview() {
    MySpeedRunnersTheme {
        RunnerRunsListScreen(
            RunnerRunsListUiState(),
            navigateToRunDetails = {},
            onBackClick = {}
        )
    }
}
