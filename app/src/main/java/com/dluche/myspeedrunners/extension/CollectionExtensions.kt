package com.dluche.myspeedrunners.extension

import androidx.compose.runtime.Composable

@Composable
fun <T> Collection<T>?.RunWithNotNullNorEmpty(block: @Composable (Collection<T>) -> Unit) {
    if(this != null && this.isNotEmpty()){
        block(this)
    }
}