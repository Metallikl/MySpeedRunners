package com.dluche.myspeedrunners.extension

import androidx.compose.runtime.Composable
import androidx.paging.LoadState

@Composable
fun LoadState.HandleStates(
    errorContent: @Composable (LoadState) -> Unit = {},
    loadingContent: @Composable (LoadState) -> Unit = {},
    notLoadingContent: @Composable (LoadState) -> Unit = {}
) {
    when (this) {
        is LoadState.Error -> errorContent(this)
        LoadState.Loading -> loadingContent(this)
        is LoadState.NotLoading -> notLoadingContent(this)
    }
}