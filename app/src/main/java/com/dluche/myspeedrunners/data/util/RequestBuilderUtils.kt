package com.dluche.myspeedrunners.data.util


import com.dluche.myspeedrunners.data.util.RequestConstants.DIRECTION_PARAM
import com.dluche.myspeedrunners.data.util.RequestConstants.EMBED_PARAM
import com.dluche.myspeedrunners.data.util.RequestConstants.OFFSET_PARAM
import com.dluche.myspeedrunners.data.util.RequestConstants.ORDER_BY_PARAM
import com.dluche.myspeedrunners.domain.QueryParams
import com.dluche.myspeedrunners.domain.model.common.EmbedParams
import com.dluche.myspeedrunners.domain.model.common.QueryOrderBy

private const val FIRST_PARAM_SYMBOL = "?"
private const val OTHER_PARAM_SYMBOL = "&"

fun QueryOrderBy?.buildOrderByInfo() = this?.let {
    "&$ORDER_BY_PARAM=${it.fieldToOrderBy}&$DIRECTION_PARAM=${it.direction}"
}.orEmpty()

fun EmbedParams?.buildEmbedInfo(isFirstParam: Boolean): String = this?.let { embedParams ->
    val symbol = getInfoSymbol(isFirstParam)
    return StringBuilder(symbol).apply {
        append("${EMBED_PARAM}=")
        embedParams.params.forEachIndexed { index, param ->
            append(param)
            if (index != embedParams.params.lastIndex) {
                append(",")
            }
        }
    }.toString()
}.orEmpty()

fun buildOffsetInfo(offset: Int?) = offset?.let {
    "&$OFFSET_PARAM=$offset"
}.orEmpty()

private fun getInfoSymbol(isFirstParam: Boolean) =
    if (isFirstParam) FIRST_PARAM_SYMBOL else OTHER_PARAM_SYMBOL

fun QueryParams?.buildQueryParamsInfo(isFirstParam: Boolean): String = this?.let{ queryParams ->
    val symbol = getInfoSymbol(isFirstParam)
    return StringBuilder(symbol).apply{
        queryParams.params.forEach { (key, value) ->
            append("$key=$value")
        }
    }.toString()
}.orEmpty()

