package com.pointlessapps.mypremiummobile.remote.datasource.settings.service

import com.pointlessapps.mypremiummobile.http.authorization.Authorize
import com.pointlessapps.mypremiummobile.remote.datasource.settings.dto.NotificationMethodsResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.settings.dto.SettingsResponseDto
import retrofit2.http.GET

internal interface SettingsService {

    @Authorize
    @GET("settings/getCustomerSettings")
    suspend fun getSettings(): SettingsResponseDto

    @Authorize
    @GET("settings/getNotificationMethods")
    suspend fun getNotificationMethods(): NotificationMethodsResponseDto
}
