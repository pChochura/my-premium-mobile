package com.pointlessapps.mypremiummobile.remote.datasource.services.dto

data class InternetPackageStatusResponseDto(
    val currentStatus: Float,
    val limit: Float,
    val limitDate: String,
)
