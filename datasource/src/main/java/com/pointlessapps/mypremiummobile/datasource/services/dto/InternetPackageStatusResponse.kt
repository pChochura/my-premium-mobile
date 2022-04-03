package com.pointlessapps.mypremiummobile.datasource.services.dto

import java.util.*

data class InternetPackageStatusResponse(
    val currentStatus: Float,
    val limit: Float,
    val limitDate: Date,
)
