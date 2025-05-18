package com.dluche.myspeedrunners.ui.feature.runnersearch.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.ui.components.RunnerCardComponent
import com.dluche.myspeedrunners.ui.feature.runnersearch.uievent.RunnersSearchEvents
import com.dluche.myspeedrunners.ui.feature.runnersearch.uistate.RunnersSearchUiState
import com.dluche.myspeedrunners.ui.feature.runnersearch.viewmodel.RunnersSearchViewModel
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun RunnersSearchRoute(
    viewModel: RunnersSearchViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    RunnersSearchScreen(
        uiState = uiState.value,
        onEvent = viewModel::dispatchEvent,
        onBackClick = onBackClick
    )
}

@Composable
fun RunnersSearchScreen(
    uiState: RunnersSearchUiState,
    onEvent: (RunnersSearchEvents) -> Unit,
    onBackClick: () -> Unit,
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
                .background(Color.LightGray)
                .padding(16.dp),
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

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CircleShape),
                    value = uiState.search,
                    onValueChange = {
                        onEvent(RunnersSearchEvents.UpdateSearch(it))
                                    },
                    label = { Text("Search") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                )
            }
            Text(
                text = stringResource(R.string.count_result_found,"0"),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center

            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        RunnerListHandler(uiState.listState)
    }
    
}

@Composable
fun RunnerListHandler(listState: RunnersSearchUiState.RunnersListUiState) {

    when(listState){
        is RunnersSearchUiState.RunnersListUiState.Error -> {
            Text(
                text = listState.message,
                modifier = Modifier.fillMaxSize(),
                color = Color.Red,
            )
        }
        RunnersSearchUiState.RunnersListUiState.Initial,
        RunnersSearchUiState.RunnersListUiState.Loading -> {

        }
        is RunnersSearchUiState.RunnersListUiState.Success -> {
            RunnersListComponent(listState.runners)
        }
    }
}

@Composable
fun RunnersListComponent(runnersList: List<RunnerCard>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ){
        items(runnersList.size){ runner ->
            RunnerCardComponent(runnerCard = runnersList[runner])
        }
    }
}


@Preview(
    showBackground = true
)
@Composable
private fun RunnersSearchScreenPreview() {
    MySpeedRunnersTheme {
        RunnersSearchScreen(
            RunnersSearchUiState(
                search = "Ronaldo"
            ),
            onEvent = {},
            onBackClick = {}
        )
    }
}
