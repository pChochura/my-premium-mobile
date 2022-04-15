package com.pointlessapps.mypremiummobile.remote.datasource.auth.dto

import androidx.annotation.Keep

@Keep
internal data class RefreshTokenResponseDto(
    val token: String,
)
