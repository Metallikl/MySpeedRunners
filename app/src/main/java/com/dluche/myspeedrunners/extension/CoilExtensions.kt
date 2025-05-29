package com.dluche.myspeedrunners.extension

import androidx.compose.runtime.Composable
import coil3.compose.AsyncImagePainter

@Composable
fun AsyncImagePainter.State.HandleState(
    emptyContent: @Composable (AsyncImagePainter.State) -> Unit = {},
    errorContent: @Composable (AsyncImagePainter.State) -> Unit = {},
    loadingContent: @Composable (AsyncImagePainter.State) -> Unit = {},
    successContent: @Composable (AsyncImagePainter.State) -> Unit = {}
){
    when(this){
        AsyncImagePainter.State.Empty -> emptyContent(this)
        is AsyncImagePainter.State.Error -> errorContent(this)
        is AsyncImagePainter.State.Loading -> loadingContent(this)
        is AsyncImagePainter.State.Success -> successContent(this)
    }
}