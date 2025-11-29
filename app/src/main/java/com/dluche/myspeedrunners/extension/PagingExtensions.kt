package com.dluche.myspeedrunners.extension

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

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

fun <T : Any> LazyPagingItems<T>.inAnyLoading(): Boolean {
    return this.loadState.append is LoadState.Loading
            || this.loadState.refresh is LoadState.Loading
            || this.loadState.prepend is LoadState.Loading
}