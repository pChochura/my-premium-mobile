package com.pointlessapps.mypremiummobile.remote.datasource.settings.di

import com.pointlessapps.mypremiummobile.datasource.settings.SettingsDatasource
import com.pointlessapps.mypremiummobile.remote.datasource.settings.SettingsDatasourceImpl
import com.pointlessapps.mypremiummobile.remote.datasource.settings.service.SettingsService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

internal val settingsModule = module {
    single<SettingsDatasource> {
        SettingsDatasourceImpl(
            settingsService = get(),
        )
    }

    single<SettingsService> {
        get<Retrofit>().create()
    }
}
