package com.dluche.myspeedrunners.data.routes

object ApiRoutes {
    const val RUNNERS_PATH = "users"

    object Runners {
        fun getSearchRunners(name: String? = null): String {
            return RUNNERS_PATH + if (name != null) "?name=$name" else ""
        }

        fun getRunnerById(name: String): String {
            return "$RUNNERS_PATH/$name"
        }
    }

}