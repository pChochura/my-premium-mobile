package com.pointlessapps.mypremiummobile.datasource.auth.dto

data class LoginResponse(
    val email: String,
    val name: String,
    val token: String,
)
