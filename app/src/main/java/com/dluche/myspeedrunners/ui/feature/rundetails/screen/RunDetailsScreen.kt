package com.dluche.myspeedrunners.ui.feature.rundetails.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun RunDetailsRoute(onBackClick: () -> Unit) {
    RunDetailsScreen(
        onBackClick = onBackClick
    )
}

@Composable
fun RunDetailsScreen(
    onBackClick: () -> Unit
) {

}

@Preview
@Composable
private fun RunDetailsScreenPreview() {
    MySpeedRunnersTheme {
        RunDetailsScreen(
            onBackClick = {}
        )
    }
}
