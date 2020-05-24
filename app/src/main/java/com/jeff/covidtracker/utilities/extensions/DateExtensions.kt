package com.jeff.covidtracker.utilities.extensions

import com.jeff.covidtracker.utilities.extensions.DateExtensions.Companion.LOCALE
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateExtensions {
    companion object {
        val LOCALE: Locale = Locale.getDefault()
    }
}

fun String.toDisplay(format: String): String =
        try {
            //2020-05-09T00:00:00Z
            val defaultParseFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", LOCALE)
            SimpleDateFormat(format, LOCALE).format(
                    defaultParseFormat.parse(this))
        } catch (e: ParseException) {
            ""
        }

fun Date.toDisplay(format: String): String =
        SimpleDateFormat(format, LOCALE).format(this)

fun String.toDisplay(parseFormat: String, displayFormat: String): String {
    return try {
        SimpleDateFormat(displayFormat, LOCALE)
                .format(SimpleDateFormat(parseFormat, LOCALE).parse(this))
    } catch (e: ParseException) {
        ""
    }
}

