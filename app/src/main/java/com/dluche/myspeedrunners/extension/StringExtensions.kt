package com.dluche.myspeedrunners.extension

import androidx.compose.runtime.Composable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.contracts.contract
import kotlin.text.isNullOrEmpty

const val DATE_ONLY = "dd/MM/yyyy"
const val DATE_TIME_SIMPLE = "dd/MM/yyyy HH:mm"

fun String.formatToDate(
    dateFormatIn: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME,
    dateFormatOut: DateTimeFormatter = DateTimeFormatter.ofPattern(DATE_ONLY)
) = try {
    LocalDate.parse(this, dateFormatIn).format(dateFormatOut)
} catch (e: Exception) {
    ""
}

fun String?.isNotNullNorEmpty(): Boolean {
    return this != null && this.isNotEmpty()
}

@Composable
fun String?.RunWithNotNullNorEmpty(block: @Composable (String) -> Unit) {
    if(this.isNotNullNorEmpty()){
        this?.let{
            block(it)
        }
    }
}