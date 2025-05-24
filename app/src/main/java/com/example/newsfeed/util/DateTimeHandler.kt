package com.example.newsfeed.util

import java.text.SimpleDateFormat
import java.util.*

object DateTimeHandler {

    fun formatUtcToReadable(dateStr: String): String {
        return try {
            val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            utcFormat.timeZone = TimeZone.getTimeZone("UTC")

            val date = utcFormat.parse(dateStr)

            val outputFormat = SimpleDateFormat("MMMM dd, yyyy 'at' hh:mm a", Locale.getDefault())
            outputFormat.timeZone = TimeZone.getDefault() // Use device time zone

            date?.let { outputFormat.format(it) } ?: "Invalid date"
        } catch (e: Exception) {
            "Invalid date"
        }
    }
}
