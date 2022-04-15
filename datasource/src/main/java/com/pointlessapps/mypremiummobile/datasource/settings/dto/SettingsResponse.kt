package com.pointlessapps.mypremiummobile.datasource.settings.dto

data class SettingsResponse(
    val login: String,
    val notificationMethod: String,
    val twoFactorAuth: Boolean,
)
