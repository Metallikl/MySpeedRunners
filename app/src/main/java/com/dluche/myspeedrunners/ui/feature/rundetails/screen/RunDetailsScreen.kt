package com.dluche.myspeedrunners.ui.feature.rundetails.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.extension.HandleState
import com.dluche.myspeedrunners.ui.components.GenericErrorWithButtonComponent
import com.dluche.myspeedrunners.ui.feature.rundetails.uievents.RunDetailsEvents
import com.dluche.myspeedrunners.ui.feature.rundetails.uistate.RunDetailsUiState
import com.dluche.myspeedrunners.ui.feature.rundetails.viewmodel.RunDetailsViewModel
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun RunDetailsRoute(onBackClick: () -> Unit) {
    val viewModel = hiltViewModel<RunDetailsViewModel>()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    RunDetailsScreen(
        uiState = uiState.value,
        onBackClick = onBackClick,
        onDispatchEvent = { viewModel.dispatchEvent(it) }
    )

    LaunchedEffect(Unit) {
        viewModel.dispatchEvent(RunDetailsEvents.LoadRunDetails)
    }
}


@Composable
fun RunDetailsScreen(
    onBackClick: () -> Unit,
    uiState: RunDetailsUiState,
    onDispatchEvent: (RunDetailsEvents) -> Unit
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
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.2f)),
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

            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
            ) {
                GameCoverComponent(uiState)

                GameNameComponent(uiState)

                CategoryComponent(uiState)

                if (uiState is RunDetailsUiState.Error) {
                    GenericErrorWithButtonComponent(
                        onRetry = {
                            onDispatchEvent(RunDetailsEvents.LoadRunDetails)
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun BackgroundComponent(state: RunDetailsUiState) {
    when (state) {
        RunDetailsUiState.Initial,
        RunDetailsUiState.Loading -> {
            Box(
                modifier = Modifier
                    .shimmer()
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            )
        }

        is RunDetailsUiState.Error -> {}
        is RunDetailsUiState.Success -> {
            BackgroundSuccess(state.run)
        }
    }
}

@Composable
private fun BackgroundSuccess(runItem: Run) {
    val backgroundPainter = rememberAsyncImagePainter(runItem.game.backgroundUrl)
    val backgroundPainterState = backgroundPainter.state.collectAsState()

    backgroundPainterState.value.HandleState(
        successContent = {
            Image(
                painter = backgroundPainter,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.4f),
                contentScale = ContentScale.Crop,
            )
        },
        errorContent = {
            Image(
                painter = backgroundPainter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        },
        loadingContent = {
            Box(
                modifier = Modifier
                    .shimmer()
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
            )
        }
    )
}

@Composable
fun GameCoverComponent(uiState: RunDetailsUiState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 16.dp),

        ) {
        if (uiState is RunDetailsUiState.Success) {
            val coverPainter = rememberAsyncImagePainter(uiState.run.game.imageUrl)
            val coverPainterState = coverPainter.state.collectAsState()

            coverPainterState.value.HandleState(
                successContent = {
                    Image(
                        painter = coverPainter,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds
                    )
                }
            )
        } else {
            Box(
                modifier = Modifier
                    .shimmer()
                    .fillMaxSize()
                    .background(Color.Gray)
            )
        }
    }
}

@Composable
fun GameNameComponent(uiState: RunDetailsUiState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {

        if (uiState is RunDetailsUiState.Success) {
            Text(
                text = uiState.run.game.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        } else {
            Box(
                modifier = Modifier
                    .shimmer()
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Color.Gray)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun CategoryComponent(uiState: RunDetailsUiState) {
    if (uiState is RunDetailsUiState.Success) {
        Text(
            text = stringResource(R.string.category_label),
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .wrapContentWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape.copy(
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp)
                    )
                )
                .padding(8.dp),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = uiState.run.category.name,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = CircleShape.copy(
                        topStart = CornerSize(0.dp),
                    )
                )
                .padding(8.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

    } else {
        Box(
            modifier = Modifier
                .shimmer()
                .fillMaxWidth()
                .height(32.dp)
                .background(Color.Gray)
        )
    }
}

@Preview
@Composable
private fun RunDetailsScreenPreview() {
    MySpeedRunnersTheme {
        RunDetailsScreen(
            uiState = RunDetailsUiState.Initial,
            onDispatchEvent = {},
            onBackClick = {},
        )
    }
}
