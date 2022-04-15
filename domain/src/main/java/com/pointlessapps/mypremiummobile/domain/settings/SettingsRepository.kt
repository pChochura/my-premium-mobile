package com.pointlessapps.mypremiummobile.domain.settings

import com.pointlessapps.mypremiummobile.datasource.settings.SettingsDatasource
import com.pointlessapps.mypremiummobile.datasource.settings.dto.NotificationMethodsResponse
import com.pointlessapps.mypremiummobile.datasource.settings.dto.SettingsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface SettingsRepository {
    fun getSettings(): Flow<SettingsResponse>
    fun getNotificationMethods(): Flow<NotificationMethodsResponse>
}

internal class SettingsRepositoryImpl(
    private val settingsDatasource: SettingsDatasource,
) : SettingsRepository {

    override fun getSettings(): Flow<SettingsResponse> = flow {
        emit(settingsDatasource.getSettings())
    }.flowOn(Dispatchers.IO)

    override fun getNotificationMethods(): Flow<NotificationMethodsResponse> = flow {
        emit(settingsDatasource.getNotificationMethods())
    }.flowOn(Dispatchers.IO)
}
