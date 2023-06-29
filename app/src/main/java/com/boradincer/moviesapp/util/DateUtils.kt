package com.boradincer.moviesapp.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    // General purpose date util class, prepared for future use too.
    companion object {
        const val DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd"
        const val DATE_FORMAT_DD_MM_YYYY = "dd.MM.yyyy"
        const val DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd_HH:mm:ss"
        const val TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

        fun getCurrentDateTime(): String {
            val dateFormat = SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS, Locale.getDefault())
            return dateFormat.format(Date())
        }

        fun getCurrentDate(): String {
            val dateFormat = SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD, Locale.getDefault())
            return dateFormat.format(Date())
        }

        fun convertDateToString(date: Date, format: String): String {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            return dateFormat.format(date)
        }

        fun convertStringToDate(dateString: String, format: String): Date {
            val dateFormat = SimpleDateFormat(format, Locale.getDefault())
            return dateFormat.parse(dateString)
        }

        fun addDaysToDate(date: Date, days: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DATE, days)
            return calendar.time
        }

        fun convertTimestampToDate(timestamp: Long): Date {
            val dateFormat = SimpleDateFormat(TIMESTAMP_FORMAT, Locale.getDefault())
            return dateFormat.parse(dateFormat.format(timestamp))
        }

        fun convertDateToTimestamp(date: Date): Long {
            val dateFormat = SimpleDateFormat(TIMESTAMP_FORMAT, Locale.getDefault())
            return dateFormat.parse(dateFormat.format(date)).time
        }

        fun convertMillisToDate(millis: Long): Date {
            return Date(millis)
        }

        fun convertDateToMillis(date: Date): Long {
            return date.time
        }

        fun convertTimestampToTime(timestamp: Long): String {
            val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            val time = Date(timestamp)
            return sdf.format(time)
        }
    }
}
