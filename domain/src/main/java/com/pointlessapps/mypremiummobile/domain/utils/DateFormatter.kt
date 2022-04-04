package com.pointlessapps.mypremiummobile.domain.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter(
    private val dateFormat: SimpleDateFormat,
    private val dateTimeFormat: SimpleDateFormat,
) {

    fun formatDate(date: Date): String = dateFormat.format(date)

    fun formatDateTime(date: Date): String = dateTimeFormat.format(date)
}
