package com.pointlessapps.mypremiummobile.datasource.auth.dto

data class LoginResponseDto(
    val email: String,
    val name: String,
    val token: String,
)
