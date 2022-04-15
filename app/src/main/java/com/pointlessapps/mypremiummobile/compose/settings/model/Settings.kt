package com.pointlessapps.mypremiummobile.compose.settings.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Settings(
    val login: String = "",
    val notificationMethod: NotificationMethod = NotificationMethod.SMS,
    val isTwoFactorAuthEnabled: Boolean = false,
) : Parcelable
