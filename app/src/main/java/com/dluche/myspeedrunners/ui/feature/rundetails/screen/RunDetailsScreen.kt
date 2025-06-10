package com.dluche.myspeedrunners.ui.feature.rundetails.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.run.Run
import com.dluche.myspeedrunners.extension.HandleState
import com.dluche.myspeedrunners.extension.RunWithNotNullNorEmpty
import com.dluche.myspeedrunners.extension.urlToEmbedded
import com.dluche.myspeedrunners.ui.components.GenericErrorWithButtonComponent
import com.dluche.myspeedrunners.ui.components.RunStatusComponent
import com.dluche.myspeedrunners.ui.components.RunWebViewContent
import com.dluche.myspeedrunners.ui.fake.run1
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

                GameNameComponent(uiState)
            }
            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                GameCoverComponent(uiState)

                ContentComponent(uiState)

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
private fun RunStatusContainer(uiState: RunDetailsUiState) {
    when (uiState) {
        RunDetailsUiState.Initial,
        RunDetailsUiState.Loading -> {
            Box(
                modifier = Modifier
                    .shimmer()
                    .fillMaxWidth(0.5f)
                    .background(MaterialTheme.colorScheme.background, CircleShape)
                    .padding(vertical = 16.dp)
            )
        }

        is RunDetailsUiState.Error -> {

        }

        is RunDetailsUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                }
                RunStatusComponent(
                    runStatus = uiState.run.status,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                )

                uiState.run.primaryTime.RunWithNotNullNorEmpty { time ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Text(
                            text = stringResource(R.string.time_label),
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .wrapContentWidth(),
                            fontWeight = Bold
                        )

                        Text(
                            modifier = Modifier
                                .wrapContentWidth(),
                            text = time,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        text = stringResource(R.string.submitted_label),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .wrapContentWidth(),
                        fontWeight = Bold
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentWidth(),
                        text = uiState.run.submitted,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}

@Composable
private fun BackgroundComponent(state: RunDetailsUiState) {
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
private fun GameCoverComponent(uiState: RunDetailsUiState) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 16.dp)
    ) {
        if (uiState is RunDetailsUiState.Success) {
            val coverPainter = rememberAsyncImagePainter(uiState.run.game.imageUrl)
            val coverPainterState = coverPainter.state.collectAsState()

            coverPainterState.value.HandleState(
                successContent = {
                    Image(
                        painter = coverPainter,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().aspectRatio(16 / 9f),
                        contentScale = ContentScale.FillBounds,

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
private fun GameNameComponent(uiState: RunDetailsUiState, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {

        if (uiState is RunDetailsUiState.Success) {
            Text(
                text = uiState.run.game.name,
                style = MaterialTheme.typography.titleMedium,
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
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Color.Gray)
            )
        }
    }
}

@Composable
private fun ContentComponent(uiState: RunDetailsUiState) {
    if (uiState is RunDetailsUiState.Success) {
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
                RunStatusContainer(uiState)

                Spacer(modifier = Modifier.height(8.dp))

                CategoryContainer(uiState)

                Spacer(modifier = Modifier.height(8.dp))

                RunLinksContent(uiState)
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
private fun RunLinksContent(uiState: RunDetailsUiState.Success) {
    uiState.run.videos.RunWithNotNullNorEmpty { links ->
        links.forEach {
            RunWebViewContent(
                url = it.urlToEmbedded(),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(200.dp),
                showUrlText = true,

            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun CategoryContainer(uiState: RunDetailsUiState.Success) {
    Text(
        text = stringResource(R.string.category_label),
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier
            .wrapContentWidth(),
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = Bold
    )

    Text(
        text = uiState.run.category.name,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.onSurface
    )

    Spacer(modifier = Modifier.height(8.dp))

    uiState.run.category.rules.RunWithNotNullNorEmpty { rules ->
        Text(
            text = stringResource(R.string.rules_label),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .wrapContentWidth(),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = Bold
        )

        Text(
            text = rules,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    uiState.run.comment.RunWithNotNullNorEmpty { commentText ->
        Text(
            text = stringResource(R.string.comment_label),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .wrapContentWidth(),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = Bold
        )

        Text(
            text = commentText,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
private fun RunDetailsScreenPreview() {
    MySpeedRunnersTheme {
        RunDetailsScreen(
            uiState = RunDetailsUiState.Success(run1),
            onDispatchEvent = {},
            onBackClick = {},
        )
    }
}
