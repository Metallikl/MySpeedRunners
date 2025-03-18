package com.dluche.myspeedrunners.ui.feature.runnerdetails.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dluche.myspeedrunners.data.datasource.model.RunnerDto
import com.dluche.myspeedrunners.ui.feature.runnerdetails.viewmodel.RunnerDetailsViewModel
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun RunnerDetailsRoute(
    viewModel: RunnerDetailsViewModel = hiltViewModel()
) {
    val uiState = viewModel.runnerState.collectAsStateWithLifecycle()
    RunnerDetailsScreen(uiState.value)
}

@Composable
fun RunnerDetailsScreen(
    uiState: RunnerDto?
) {

   uiState?.let {
       Text(
           text = it.toString(),
           modifier = Modifier.fillMaxWidth()
       )
   }

}

@Preview
@Composable
private fun RunnerDetailsScreenPreview() {
    MySpeedRunnersTheme{
        RunnerDetailsScreen(
            uiState = null
        )
    }
}
