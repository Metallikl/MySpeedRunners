package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieConstants
import com.dluche.myspeedrunners.R

@Composable
fun GenericErrorWithButtonComponent(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    errorText: String = stringResource(R.string.error_something_went_wrong_lbl),
    buttonText: String = stringResource(R.string.retry),
    animationSize: Dp = 150.dp,
    animationResId: Int = R.raw.error_full,
    interaction: Int = LottieConstants.IterateForever
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier =
                Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedLottieContent(
                modifier = Modifier.size(animationSize),
                resId = animationResId,
                iterations = interaction
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = errorText,
                color = MaterialTheme.colorScheme.error,
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(onClick = { onRetry() }) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = "Refresh Icon"
                )

                Spacer(modifier = Modifier.size(8.dp))

                Text(text = buttonText)
            }
        }
    }
}