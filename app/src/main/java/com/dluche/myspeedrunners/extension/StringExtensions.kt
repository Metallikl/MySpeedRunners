package com.dluche.myspeedrunners.extension

import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
