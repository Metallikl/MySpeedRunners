package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RunsSkeletonList(modifier: Modifier = Modifier, count: Int = 10) {
    Column(
        modifier = modifier
    ) {
        for (i in 1..count) {
            RunCardSkeleton()
        }
    }
}