package com.pointlessapps.mypremiummobile.domain.model

import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.PhoneNumberResponse
import com.pointlessapps.mypremiummobile.datasource.settings.dto.NotificationMethodsResponse
import com.pointlessapps.mypremiummobile.datasource.settings.dto.SettingsResponse

data class SettingsModel(
    val userInfo: UserInfoResponse,
    val phoneNumber: PhoneNumberResponse,
    val settings: SettingsResponse,
    val notificationMethods: NotificationMethodsResponse,
)
