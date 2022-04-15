package com.pointlessapps.mypremiummobile.remote.datasource.services.dto

import androidx.annotation.Keep

@Keep
data class InternetPackageResponseDto(
    val id: Int,
    val name: String,
    val amount: String,
)
