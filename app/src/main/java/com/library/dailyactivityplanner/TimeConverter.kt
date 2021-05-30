package com.library.dailyactivityplanner

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object TimeConverter{
    val YYYY_MM_DD = "yyyy-MM-dd"
    val DD = "dd"
    val MM = "MM"
    val HH = "HH"
    val YYYY = "yyyy"
    val MMM_YYYY = "MMM yyyy"
    val YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
    val DD_MMMM_YYYY = "dd MMMM yyyy"
    val DD_MMM_YYYY = "dd MMM yyyy"
    val DD__MM__YYYY = "dd/MM/yyyy"
    val EEE_DD__MMM__YYYY = "EEE, dd MMM yyyy"
    val DD_MM_YYYY = "dd-MM-yyyy"
    val EEEE_DD_MMMM_YYYY_HH_MM = "EEEE, dd MMMM yyyy, HH:mm"
    val EEEE_DD_MMMM_YYYY_DIVIDER_HH_MM = "EEEE, dd MMMM yyyy | HH:mm"
    val DD_MM_AT_HH_MM = "dd MMM, HH:mm"
    val HH_MM = "HH:mm"
    val EEEE_DD_MMMM_YYYY = "EEEE, dd MMMM yyyy"
    val EEEE_D_MMM_YYYY_HH_MM_SS_Z = "EEEE, d MMM yyyy HH:mm:ss z"
    val DD_MM_YY_HH_MM = "dd-MM-yy, HH:mm"
    val DD_MMMM_YY_HH_MM = "dd MMMM yyyy\n HH:mm"
    val DD_MM_YY_HH_MM_SS = "dd-MM-yy HH:mm:ss"
    val MM_DD_YYYY_at_HH_MM = "MMM dd, yyyy 'at' HH:mm"
    val MM_DD_YYYY = "MMMM dd, yyyy"
    val DD_MM_YY_HH_MM_SLASH = "dd/MMM/yy HH:mm"
    val SS = "ss"
    val MM_ = "mm"

    fun convertDate(time: String, fromTimeFormat: String, toTimeFormat: String): String {
        val simpleDateFormat = SimpleDateFormat(fromTimeFormat, Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT+7")
        val simpleDateFormat2 = SimpleDateFormat(toTimeFormat, Locale.getDefault())
        simpleDateFormat2.timeZone = TimeZone.getDefault()
        val timeConverted = simpleDateFormat2.format(simpleDateFormat.parse(time))
        return timeConverted
    }

    fun getDate(milliSeconds: Long, dateFormat: String): String {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }
    fun getTime(time: String): Long {
        val sdp = SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale("in"))
        val d = sdp.parse(time)
        return d.time
    }
}