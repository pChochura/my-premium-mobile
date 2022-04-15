package com.pointlessapps.mypremiummobile.compose.settings.di

import com.pointlessapps.mypremiummobile.compose.settings.ui.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val settingsModule = module {
    viewModel {
        SettingsViewModel(
            errorHandler = get(),
            getSettingsModelUseCase = get(),
            logoutUseCase = get(),
        )
    }
}
