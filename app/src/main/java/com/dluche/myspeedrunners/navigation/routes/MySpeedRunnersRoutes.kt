package com.dluche.myspeedrunners.navigation.routes

import kotlinx.serialization.Serializable

sealed interface MySpeedRunnersRoutes {

    @Serializable
    object RunnersSearch

    @Serializable
    data class RunnerDetails(val runnerId: String)

}