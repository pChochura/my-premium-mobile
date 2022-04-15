package com.pointlessapps.mypremiummobile.remote.datasource.services.dto

import androidx.annotation.Keep

@Keep
data class PhoneNumberResponseDto(
    val id: String,
    val msisdn: String,
    val isMainNumber: Boolean,
)
