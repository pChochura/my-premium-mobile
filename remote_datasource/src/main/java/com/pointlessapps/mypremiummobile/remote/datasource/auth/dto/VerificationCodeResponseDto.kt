package com.pointlessapps.mypremiummobile.remote.datasource.auth.dto

import androidx.annotation.Keep

@Keep
data class VerificationCodeResponseDto(
    val success: Boolean,
    val message: String,
)
