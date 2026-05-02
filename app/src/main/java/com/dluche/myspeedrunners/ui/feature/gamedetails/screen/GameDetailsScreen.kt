package com.dluche.myspeedrunners.ui.feature.gamedetails.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.dluche.myspeedrunners.ui.components.RunnerCardComponent
import com.dluche.myspeedrunners.ui.feature.gamedetails.model.GameDetailsBottomSheetType
import com.dluche.myspeedrunners.ui.feature.gamedetails.uievents.GameDetailsEvents
import com.dluche.myspeedrunners.ui.feature.gamedetails.uistate.GameDetailsUiState
import com.dluche.myspeedrunners.ui.feature.gamedetails.viewmodel.GameDetailsViewModel
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.valentinilk.shimmer.shimmer

@ExperimentalMaterial3Api
@Composable
fun GameDetailsRoute(
    navigateToRunnerDetails: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel = hiltViewModel<GameDetailsViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var bottomSheetType by remember { mutableStateOf(GameDetailsBottomSheetType.NONE) }

    GameDetailsScreen(
        uiState = uiState.value,
        onDispatchEvent = { viewModel.dispatchEvent(it) },
        onBackClick = onBackClick,
        navigateToRunnerDetails = navigateToRunnerDetails,
        onChangeBottomSheetType = {
            bottomSheetType = it
        }
    )

    LaunchedEffect(Unit) {
        viewModel.dispatchEvent(GameDetailsEvents.LoadGameDetails)
    }

    when (bottomSheetType) {
        GameDetailsBottomSheetType.PLATFORM, GameDetailsBottomSheetType.CATEGORY -> {
            if (uiState.value is GameDetailsUiState.Success) {
                ModalBottomSheet(
                    onDismissRequest = { bottomSheetType = GameDetailsBottomSheetType.NONE },
                    sheetState = sheetState
                ) {
                    if (bottomSheetType == GameDetailsBottomSheetType.PLATFORM) {
                        PlatformContainer(uiState.value as GameDetailsUiState.Success)
                    } else {
                        CategoryContainer(uiState.value as GameDetailsUiState.Success)
                    }
                }
            }
        }

        GameDetailsBottomSheetType.NONE -> {}
    }
}

@Composable
fun GameDetailsScreen(
    uiState: GameDetailsUiState = GameDetailsUiState.Loading,
    onDispatchEvent: (GameDetailsEvents) -> Unit = {},
    onBackClick: () -> Unit = {},
    navigateToRunnerDetails: (String) -> Unit,
    onChangeBottomSheetType: (GameDetailsBottomSheetType) -> Unit = {},
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
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                if (uiState !is GameDetailsUiState.Error) {

                    GameNameComponent(uiState)

                    GameCover(uiState, onChangeBottomSheetType)

                    ContentComponent(uiState, navigateToRunnerDetails)

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
    uiState: GameDetailsUiState,
    onChangeBottomSheetType: (GameDetailsBottomSheetType) -> Unit
) {
    when (uiState) {
        GameDetailsUiState.Loading -> {
            GameCoverComponent(
                imageUrl = "",
                isLoading = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        is GameDetailsUiState.Success -> {
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

        is GameDetailsUiState.Error -> {}
    }
}

@Composable
fun BackgroundComponent(state: GameDetailsUiState) {
    when (state) {
        GameDetailsUiState.Loading -> {
            Box(
                modifier = Modifier
                    .shimmer()
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )
        }

        is GameDetailsUiState.Error -> {}
        is GameDetailsUiState.Success -> {
            BackgroundImageComponent(
                backgroundUrl = state.game.backgroundUrl,
            )
        }
    }
}

@Composable
fun GameNameComponent(uiState: GameDetailsUiState, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {

        when (uiState) {
            is GameDetailsUiState.Error -> {
            }

            GameDetailsUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .shimmer()
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth()
                        .height(32.dp)
                        .background(Color.Gray)
                )
            }

            is GameDetailsUiState.Success -> {
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
    navigateToRunnerDetails: (String) -> Unit
) {
    if (uiState is GameDetailsUiState.Success) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardDefaults.cardColors().containerColor.copy(
                    alpha = 0.5f
                )
            )
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                ModeratorsContainer(uiState, navigateToRunnerDetails)
            }
        }
    } else {
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

@Composable
fun ModeratorsContainer(
    uiState: GameDetailsUiState.Success,
    navigateToRunnerDetails: (String) -> Unit
) {

    uiState.game.moderators.RunWithNotNullNorEmpty { runners ->
        Text(
            text = stringResource(R.string.moderators),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .wrapContentWidth(),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = Bold
        )

        runners.forEach { runner ->
            RunnerCardComponent(
                runnerCard = runner,
                onClick = { navigateToRunnerDetails(runner.id) }

            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun PlatformContainer(uiState: GameDetailsUiState.Success) {
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
private fun CategoryContainer(uiState: GameDetailsUiState.Success) {

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
        GameDetailsScreen(navigateToRunnerDetails = { })
    }
}
