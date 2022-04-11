package com.pointlessapps.mypremiummobile.compose.login.di

import com.pointlessapps.mypremiummobile.compose.login.ui.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val loginModule = module {
    viewModel {
        LoginViewModel(
            errorHandler = get(),
            loginUseCase = get(),
            isLoggedInUseCase = get(),
            getCredentialsUseCase = get(),
            saveCredentialsUseCase = get(),
            validateSimpleInputUseCase = get(),
        )
    }
}
