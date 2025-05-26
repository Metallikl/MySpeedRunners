package com.dluche.myspeedrunners.data.routes

object ApiRoutes {
    const val RUNNERS_PATH = "users"

    object Runners {
        fun getSearchRunners(name: String? = null, offset: Int? = null): String {
            val nameFilter = if (name != null) "?name=$name" else ""
            val offsetFilter = if (offset != null) "&offset=$offset" else ""
            return RUNNERS_PATH + nameFilter + offsetFilter
        }

        fun getRunnerById(name: String): String {
            return "$RUNNERS_PATH/$name"
        }
    }

}