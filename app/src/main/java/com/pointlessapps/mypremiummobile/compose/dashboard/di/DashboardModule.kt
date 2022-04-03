package com.pointlessapps.mypremiummobile.compose.dashboard.di

import com.pointlessapps.mypremiummobile.compose.dashboard.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val dashboardModule = module {
    viewModel { params ->
        DashboardViewModel(
            userInfo = params.get(),
            errorHandler = get(),
            getUserPhoneNumbersUseCase = get(),
            getBalanceUseCase = get(),
        )
    }
}
