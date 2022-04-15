package com.pointlessapps.mypremiummobile.remote.datasource.payments.dto

import androidx.annotation.Keep

@Keep
internal data class DateRangeBodyDto(
    val startDate: String,
    val endDate: String,
)
