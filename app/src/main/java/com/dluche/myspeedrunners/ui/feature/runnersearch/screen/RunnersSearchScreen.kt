package com.dluche.myspeedrunners.ui.feature.runnersearch.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.extension.shimmerEffect
import com.dluche.myspeedrunners.ui.components.RunnerCardComponent
import com.dluche.myspeedrunners.ui.feature.runnersearch.uievent.RunnersSearchEvents
import com.dluche.myspeedrunners.ui.feature.runnersearch.uistate.RunnersSearchUiState
import com.dluche.myspeedrunners.ui.feature.runnersearch.viewmodel.RunnersSearchViewModel
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun RunnersSearchRoute(
    viewModel: RunnersSearchViewModel = hiltViewModel(),
    navigateToRunnerDetails: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val pagerRunnersList = viewModel.runners.collectAsLazyPagingItems()
    RunnersSearchScreen(
        uiState = uiState.value,
        pagerRunnersList = pagerRunnersList,
        onEvent = viewModel::dispatchEvent,
        navigateToRunnerDetails = navigateToRunnerDetails,
        onBackClick = onBackClick
    )
}

@Composable
fun RunnersSearchScreen(
    uiState: RunnersSearchUiState,
    onEvent: (RunnersSearchEvents) -> Unit,
    navigateToRunnerDetails: (String) -> Unit = {},
    onBackClick: () -> Unit,
    pagerRunnersList: LazyPagingItems<RunnerCard>,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = {
                        onBackClick
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

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, CircleShape),
                    value = uiState.search,
                    onValueChange = {
                        onEvent(RunnersSearchEvents.UpdateSearch(it))
                    },
                    placeholder = { Text(stringResource(R.string.search_runner)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon"
                        )
                    }
                )
            }
//            if (uiState.listState is RunnersSearchUiState.RunnersListUiState.Success) {
//                Text(
//                    text = stringResource(
//                        R.string.count_result_found,
//                        uiState.listState.runners.size.
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    textAlign = TextAlign.Center
//                )
//            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        RunnerListHandler(
            listState = pagerRunnersList,
            navigateToRunnerDetails = navigateToRunnerDetails
        )
    }

}

@Composable
fun RunnerListHandler(
    listState: LazyPagingItems<RunnerCard>,
    navigateToRunnerDetails: (String) -> Unit
) {

    when (val state = listState.loadState.refresh) {
        is LoadState.Error ->
            Text(
                text = state.error.message ?: "Unknown error",
                modifier = Modifier.fillMaxSize(),
                color = Color.Red,
            )

        LoadState.Loading -> {
            RunnersListLoadingComponent()
        }

        is LoadState.NotLoading -> {
            if (listState.itemCount == 0) {
                RunnerInitialComponent()
            } else {

                RunnersListComponent(
                    runnersList = listState,
                    navigateToRunnerDetails = navigateToRunnerDetails
                )
            }
        }
    }
}

@Composable
fun RunnerInitialComponent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .padding(8.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search Icon",
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
            Text(
                text = stringResource(R.string.search_runner),
                modifier = Modifier
                    .padding(8.dp),
            )
        }
    }
}

@Composable
fun RunnersListComponent(
    runnersList: LazyPagingItems<RunnerCard>,
    navigateToRunnerDetails: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(
            count = runnersList.itemCount,
            key = { idx -> runnersList[idx]?.id.orEmpty() }
        ) { idx ->
            runnersList[idx]?.let {
                RunnerCardComponent(
                    runnerCard = it,
                    onClick = { navigateToRunnerDetails(it.id) }
                )
            }

        }
    }
}

@Composable
fun RunnersListLoadingComponent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(10) { runner ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)

                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
private fun RunnersSearchScreenPreview() {
    val pagerRunnersList = flowOf(
        PagingData.from(
            listOf<RunnerCard>(),
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(false),
                prepend = LoadState.NotLoading(false),
                append = LoadState.NotLoading(false)
            )
        )
    ).collectAsLazyPagingItems()
    MySpeedRunnersTheme {
        RunnersSearchScreen(
            RunnersSearchUiState(
                search = "Ronaldo"
            ),
            onEvent = {},
            onBackClick = {},
            pagerRunnersList = pagerRunnersList
        )
    }
}
