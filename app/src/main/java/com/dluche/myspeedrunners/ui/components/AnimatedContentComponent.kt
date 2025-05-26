package com.dluche.myspeedrunners.ui.components

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun AnimatedLottieContent(
    modifier: Modifier = Modifier,
    @RawRes resId: Int = R.raw.lottie_empty_list,
    iterations: Int = LottieConstants.IterateForever
) {
    val composition by  rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(resId)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterations,
        isPlaying = true
    )

    LottieAnimation(
        composition = composition,
        progress = {
            progress
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun AnimatedContentPreview() {
    MySpeedRunnersTheme {
        AnimatedLottieContent()
    }

}