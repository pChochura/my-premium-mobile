package com.pointlessapps.mypremiummobile.datasource.settings

import com.pointlessapps.mypremiummobile.datasource.settings.dto.NotificationMethodsResponse
import com.pointlessapps.mypremiummobile.datasource.settings.dto.SettingsResponse

interface SettingsDatasource {
    suspend fun getSettings(): SettingsResponse

    suspend fun getNotificationMethods(): NotificationMethodsResponse
}
