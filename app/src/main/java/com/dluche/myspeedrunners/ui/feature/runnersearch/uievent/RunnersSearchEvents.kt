package com.dluche.myspeedrunners.ui.feature.runnersearch.uievent

sealed interface RunnersSearchEvents {
    data class UpdateSearch(val search: String): RunnersSearchEvents
}