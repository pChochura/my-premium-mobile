package com.pointlessapps.mypremiummobile.remote.datasource.settings.mapper

import com.pointlessapps.mypremiummobile.datasource.settings.dto.NotificationMethodsResponse
import com.pointlessapps.mypremiummobile.datasource.settings.dto.SettingsResponse
import com.pointlessapps.mypremiummobile.remote.datasource.settings.dto.NotificationMethodsResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.settings.dto.SettingsResponseDto

internal fun SettingsResponseDto.toSettings() = SettingsResponse(
    login = login,
    notificationMethod = notificationMethod,
    twoFactorAuth = twoFactorAuth,
)

internal fun NotificationMethodsResponseDto.toNotificationMethods() = NotificationMethodsResponse(
    sms = sms,
    email = email,
)
