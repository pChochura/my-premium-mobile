package com.pointlessapps.mypremiummobile.remote.datasource.auth.dto

import androidx.annotation.Keep

@Keep
data class ValidateCodeBodyDto(
    val oneTimeCode: String,
    val twoFactorToken: String,
)
