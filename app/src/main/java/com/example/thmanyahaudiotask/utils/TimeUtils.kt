package com.example.thmanyahaudiotask.utils

import java.time.Duration
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

fun formatDuration(durationInSeconds: Long?): String {
    if (durationInSeconds == null || durationInSeconds <= 0) return ""

    val hours = durationInSeconds / 3600
    val minutes = (durationInSeconds % 3600) / 60

    return when {
        hours > 0 -> "${hours}h ${minutes}m"
        else -> "${minutes}m"
    }
}

fun formatTimeAgo(isoDate: String?): String {
    if (isoDate.isNullOrEmpty()) return ""

    return try {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val parsedDate = OffsetDateTime.parse(isoDate, formatter)
        val now = OffsetDateTime.now(ZoneOffset.UTC)

        val duration = Duration.between(parsedDate, now)

        when {
            duration.toMinutes() < 1 -> "just now"
            duration.toMinutes() < 60 -> "${duration.toMinutes()} min ago"
            duration.toHours() < 24 -> "${duration.toHours()} hours ago"
            duration.toDays() == 1L -> "yesterday"
            duration.toDays() < 7 -> "${duration.toDays()} days ago"
            else -> parsedDate.toLocalDate().toString()
        }
    } catch (e: Exception) {
        ""
    }
}

