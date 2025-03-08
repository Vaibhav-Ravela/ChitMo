package com.example.chitmo.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object TimeUtils {
    fun convertMonthYearStringToReadableMonthYear(monthYear: String): String =
        SimpleDateFormat("MMM, yyyy", Locale.ENGLISH).format(
            SimpleDateFormat(
                "MM-yyyy", Locale.ENGLISH
            ).parse(monthYear)!!
        )

    fun addMonthsToMonthYearAndReturnReadableMonthYear(
        monthYear: String, monthsToAdd: Int
    ): String {
        val dateFormat = SimpleDateFormat("MM-yyyy", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.time = dateFormat.parse(monthYear)!!
        calendar.add(Calendar.MONTH, monthsToAdd)
        return SimpleDateFormat("MMM, yyyy", Locale.ENGLISH).format(calendar.time)
    }
}