package com.pointlessapps.mypremiummobile.remote.datasource.settings.dto

data class SettingsResponseDto(
    val login: String,
    val notificationMethod: String,
    val twoFactorAuth: Boolean,
)
