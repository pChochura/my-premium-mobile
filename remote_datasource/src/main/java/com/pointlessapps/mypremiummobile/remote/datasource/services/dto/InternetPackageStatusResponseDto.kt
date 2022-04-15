package com.pointlessapps.mypremiummobile.remote.datasource.services.dto

import androidx.annotation.Keep

@Keep
data class InternetPackageStatusResponseDto(
    val currentStatus: Float,
    val limit: Float,
    val limitDate: String,
)
