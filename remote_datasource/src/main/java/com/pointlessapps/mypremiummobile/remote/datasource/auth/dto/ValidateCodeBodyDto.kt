package com.pointlessapps.mypremiummobile.remote.datasource.auth.dto

data class ValidateCodeBodyDto(
    val oneTimeCode: String,
    val twoFactorToken: String,
)
