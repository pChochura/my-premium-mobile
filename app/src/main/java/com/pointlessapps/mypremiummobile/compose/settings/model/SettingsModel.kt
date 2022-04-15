package com.pointlessapps.mypremiummobile.compose.settings.model

import android.os.Parcelable
import com.pointlessapps.mypremiummobile.compose.model.UserInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class SettingsModel(
    val userInfo: UserInfo,
    val settings: Settings,
    val notificationMethods: List<NotificationMethod>,
) : Parcelable
