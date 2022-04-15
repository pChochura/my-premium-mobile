package com.pointlessapps.mypremiummobile.remote.datasource.settings

import com.pointlessapps.mypremiummobile.datasource.settings.SettingsDatasource
import com.pointlessapps.mypremiummobile.datasource.settings.dto.NotificationMethodsResponse
import com.pointlessapps.mypremiummobile.datasource.settings.dto.SettingsResponse
import com.pointlessapps.mypremiummobile.remote.datasource.settings.mapper.toNotificationMethods
import com.pointlessapps.mypremiummobile.remote.datasource.settings.mapper.toSettings
import com.pointlessapps.mypremiummobile.remote.datasource.settings.service.SettingsService

internal class SettingsDatasourceImpl(
    private val settingsService: SettingsService,
) : SettingsDatasource {

    override suspend fun getSettings(): SettingsResponse =
        settingsService.getSettings().toSettings()

    override suspend fun getNotificationMethods(): NotificationMethodsResponse =
        settingsService.getNotificationMethods().toNotificationMethods()
}
