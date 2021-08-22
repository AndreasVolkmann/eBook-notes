package me.avo.ebook.notes.config

import java.time.format.DateTimeFormatter
import java.util.*

data class DateConfiguration(
    val timestampDelimiter: String,
    val dateTimeFormat: String,
    val locale: String? = null,
) {
    val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat, getLocale())

    private fun getLocale(): Locale = locale?.let { Locale.forLanguageTag(it) } ?: Locale.ROOT
}