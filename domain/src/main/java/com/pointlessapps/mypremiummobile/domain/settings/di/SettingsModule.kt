package com.pointlessapps.mypremiummobile.domain.settings.di

import com.pointlessapps.mypremiummobile.domain.settings.SettingsRepository
import com.pointlessapps.mypremiummobile.domain.settings.SettingsRepositoryImpl
import com.pointlessapps.mypremiummobile.domain.settings.usecase.GetNotificationMethodsUseCase
import com.pointlessapps.mypremiummobile.domain.settings.usecase.GetSettingsUseCase
import org.koin.dsl.module

internal val settingsModule = module {
    single<SettingsRepository> {
        SettingsRepositoryImpl(
            settingsDatasource = get(),
        )
    }

    factory {
        GetSettingsUseCase(
            settingsRepository = get(),
        )
    }

    factory {
        GetNotificationMethodsUseCase(
            settingsRepository = get(),
        )
    }
}
