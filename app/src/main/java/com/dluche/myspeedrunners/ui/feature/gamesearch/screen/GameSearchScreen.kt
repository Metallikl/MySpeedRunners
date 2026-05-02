package com.dluche.myspeedrunners.ui.feature.gamesearch.screen

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.game.GameCard
import com.dluche.myspeedrunners.extension.HandleStates
import com.dluche.myspeedrunners.extension.inAnyLoading
import com.dluche.myspeedrunners.extension.shimmerEffect
import com.dluche.myspeedrunners.ui.components.GameCardComponent
import com.dluche.myspeedrunners.ui.components.GameCardGrid
import com.dluche.myspeedrunners.ui.components.GameFilterGridCardSkeleton
import com.dluche.myspeedrunners.ui.components.GenericErrorWithButtonComponent
import com.dluche.myspeedrunners.ui.feature.gamesearch.uievent.GameSearchEvents
import com.dluche.myspeedrunners.ui.feature.gamesearch.uistate.GameSearchUiState
import com.dluche.myspeedrunners.ui.feature.gamesearch.viewmodel.GameSearchViewModel
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun GameSearchRoute(
    viewModel: GameSearchViewModel = hiltViewModel(),
    navigateToGameDetails: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    GameSearchScreen(
        uiState = uiState.value,
        onEvent = viewModel::dispatchEvent,
        navigateToGameDetails = navigateToGameDetails,
        onBackClick = onBackClick
    )
}

@Composable
fun GameSearchScreen(
    uiState: GameSearchUiState,
    onEvent: (GameSearchEvents) -> Unit,
    navigateToGameDetails: (String) -> Unit = {},
    onBackClick: () -> Unit,
) {
    val pagingState = uiState.games.collectAsLazyPagingItems()
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
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
//                IconButton(
//                    onClick = {
//                        onBackClick
//                    },
//                    colors = IconButtonDefaults.iconButtonColors(
//                        contentColor = MaterialTheme.colorScheme.onBackground
//                    )
//                ) {
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                        contentDescription = "Back"
//                    )
//                }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface, CircleShape),
                    value = uiState.search,
                    onValueChange = {
                        onEvent(GameSearchEvents.UpdateSearch(it))
                    },
                    placeholder = { Text(stringResource(R.string.search_game)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon"
                        )
                    }
                )
            }

            if (
                !pagingState.loadState.hasError
                && !pagingState.inAnyLoading()
                && pagingState.itemCount > 0
            ) {
                Text(
                    text = stringResource(
                        R.string.count_result_found,
                        pagingState.itemCount
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        GameListHandler(
            gamePagingState = pagingState,
            navigateToGameDetails = navigateToGameDetails
        )
    }

}

@Composable
fun GameInitialComponent() {
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
                text = stringResource(R.string.search_game),
                modifier = Modifier
                    .padding(8.dp),
            )
        }
    }
}

@Composable
fun GameListComponent(
    gamesList: LazyPagingItems<GameCard>,
    navigateToGameDetails: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(
            count = gamesList.itemCount,
            key = { idx -> gamesList[idx]?.id.orEmpty() }) { idx ->
            gamesList[idx]?.let {
                GameCardGrid(
                    game = it,
                    size = 100.dp,
                    onClick = {
                        navigateToGameDetails(it.id)
                    }
                )
            }
        }
        item {
            gamesList.loadState.append.HandleStates(
                errorContent = { PagingAppendError(gamesList) },
                loadingContent = {
                    GameFilterGridCardSkeleton(size = 100.dp)
                }
            )
        }
    }
}

@Composable
fun GameListHandler(
    gamePagingState: LazyPagingItems<GameCard>,
    navigateToGameDetails: (String) -> Unit
) {
    gamePagingState.loadState.refresh.HandleStates(
        errorContent = {
            GenericErrorWithButtonComponent(
                onRetry = { gamePagingState.refresh() },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                interaction = 1
            )
        },
        loadingContent = {
            GameListLoadingComponent()
        },
        notLoadingContent = {
            if (gamePagingState.itemCount == 0) {
                GameInitialComponent()
            } else {
                GameListComponent(
                    gamesList = gamePagingState,
                    navigateToGameDetails = navigateToGameDetails
                )
            }
        }
    )
}

@Composable
private fun PagingAppendError(gamesList: LazyPagingItems<GameCard>) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center

    ) {
        OutlinedButton(onClick = { gamesList.retry() }) {
            Icon(
                imageVector = Icons.Outlined.Refresh,
                contentDescription = "Refresh Icon"
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(text = stringResource(R.string.retry))
        }
    }
}

@Composable
fun GameListLoadingComponent() {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(
            count = 12
        ) {
            GameFilterGridCardSkeleton(size = 100.dp)
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
private fun GameSearchScreenPreview() {
    MySpeedRunnersTheme {
        GameSearchScreen(
            GameSearchUiState(
                search = "Ronaldo"
            ),
            onEvent = {},
            onBackClick = {},
        )
    }
}
