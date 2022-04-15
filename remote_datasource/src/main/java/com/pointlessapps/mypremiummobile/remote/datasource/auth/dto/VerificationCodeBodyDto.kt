package com.pointlessapps.mypremiummobile.remote.datasource.auth.dto

import androidx.annotation.Keep

@Keep
data class VerificationCodeBodyDto(
    val twoFactorToken: String,
)
