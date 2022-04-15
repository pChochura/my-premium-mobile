package com.pointlessapps.mypremiummobile.remote.datasource.settings.dto

import androidx.annotation.Keep

@Keep
data class NotificationMethodsResponseDto(
    val sms: Boolean,
    val email: Boolean,
)
