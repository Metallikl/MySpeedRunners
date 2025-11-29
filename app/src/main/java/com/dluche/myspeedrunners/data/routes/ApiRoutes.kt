package com.dluche.myspeedrunners.data.routes

import com.dluche.myspeedrunners.data.util.buildEmbedInfo
import com.dluche.myspeedrunners.data.util.buildOrderByInfo
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy

object ApiRoutes {
    const val RUNNERS_PATH = "users"
    const val PERSONAL_BEST_PATH = "personal-bests"

    object Runners {
        fun getSearchRunners(name: String? = null, offset: Int? = null): String {
            val nameFilter = if (name != null) "?name=$name" else ""
            val offsetFilter = if (offset != null) "&offset=$offset" else ""
            return RUNNERS_PATH + nameFilter + offsetFilter
        }

        fun getRunnerById(name: String): String {
            return "$RUNNERS_PATH/$name"
        }

        fun getRunnerPersonalBest(
            runnerId: String,
            embedParams: EmbedParams?,
            queryOrderBy: QueryOrderBy?
        ): String{
            return "$RUNNERS_PATH/$runnerId/$PERSONAL_BEST_PATH${embedParams.buildEmbedInfo(true)}${queryOrderBy.buildOrderByInfo()}"
        }
    }

}