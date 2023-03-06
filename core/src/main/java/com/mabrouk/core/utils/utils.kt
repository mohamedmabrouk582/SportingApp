package com.mabrouk.core.utils

import android.util.Log
import androidx.test.services.events.TimeStamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * @name Mohamed Mabrouk
 * Copyright (c) 2/9/23
 */

fun dateFormater(milliseconds : Long?) : String?{
    milliseconds?.let{
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = it
        return formatter.format(calendar.time)
    }
    return null
}

fun getCurrentDate(pattern: String = "yyyy-MM-dd"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return formatter.format(LocalDateTime.now())
}

fun getDate(date: String, currentFormat: String="yyyy-MM-dd", pattern: String = "yyyy-MM-dd"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val finaDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(currentFormat)).atStartOfDay(
        ZoneId.systemDefault()
    )
    finaDate.zone.apply {
        Log.d("Zone", this.id ?: "")
    }
    return formatter.format(finaDate)
}

fun convertDate(date:String,pattern: String = "yyyy-MM-dd"):LocalDate{
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val finaDate = LocalDate.parse(date,formatter)
    return finaDate
}

fun isAfterCurrent(time: String): Boolean {
    val split = time.split(":")
    val hour = split[0].toInt()
    val min = split[1].toInt()
    val current = LocalDateTime.now()
    val alarmTime = LocalDateTime.of(current.year, current.month, current.dayOfMonth, hour, min)
    return current.isBefore(alarmTime)
}

