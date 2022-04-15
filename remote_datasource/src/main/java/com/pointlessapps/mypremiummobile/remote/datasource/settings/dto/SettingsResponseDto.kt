package com.pointlessapps.mypremiummobile.remote.datasource.settings.dto

import androidx.annotation.Keep

@Keep
data class SettingsResponseDto(
    val login: String,
    val notificationMethod: String,
    val twoFactorAuth: Boolean,
)
