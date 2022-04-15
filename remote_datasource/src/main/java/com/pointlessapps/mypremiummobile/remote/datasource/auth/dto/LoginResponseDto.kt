package com.pointlessapps.mypremiummobile.remote.datasource.auth.dto

import androidx.annotation.Keep

@Keep
internal data class LoginResponseDto(
    val email: String?,
    val name: String?,
    val token: String?,
)
