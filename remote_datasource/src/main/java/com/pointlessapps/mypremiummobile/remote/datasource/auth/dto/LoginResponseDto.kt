package com.pointlessapps.mypremiummobile.remote.datasource.auth.dto

internal data class LoginResponseDto(
    val email: String,
    val name: String,
    val token: String?,
)
