package com.pointlessapps.mypremiummobile.domain.auth.model

data class LoginResponse(
    val email: String,
    val name: String,
    val token: String,
)
