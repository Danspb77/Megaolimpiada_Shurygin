package com.dev.sport.util

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateTimeFormatters {
    private val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
    private val apiFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    private val inputFormatters = listOf(
        DateTimeFormatter.ISO_OFFSET_DATE_TIME,
        DateTimeFormatter.ISO_ZONED_DATE_TIME,
        DateTimeFormatter.ISO_LOCAL_DATE_TIME,
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    )

    fun formatUi(raw: String?): String {
        if (raw.isNullOrBlank()) return "-"
        val trimmed = raw.trim()
        for (formatter in inputFormatters) {
            try {
                val parsed = when (formatter) {
                    DateTimeFormatter.ISO_OFFSET_DATE_TIME -> OffsetDateTime.parse(trimmed, formatter).toLocalDateTime()
                    DateTimeFormatter.ISO_ZONED_DATE_TIME -> ZonedDateTime.parse(trimmed, formatter).toLocalDateTime()
                    else -> LocalDateTime.parse(trimmed, formatter)
                }
                return parsed.format(outputFormatter)
            } catch (_: DateTimeParseException) {
                // try next
            }
        }
        return trimmed
    }

    fun formatApi(value: LocalDateTime): String = value.format(apiFormatter)
}
