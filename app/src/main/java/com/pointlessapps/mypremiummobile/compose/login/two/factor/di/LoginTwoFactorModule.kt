package com.pointlessapps.mypremiummobile.compose.login.two.factor.di

import com.pointlessapps.mypremiummobile.compose.login.two.factor.ui.LoginTwoFactorViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val loginTwoFactorModule = module {
    viewModel {
        LoginTwoFactorViewModel(
            errorHandler = get(),
            validateSimpleInputUseCase = get(),
            sendVerificationCodeUseCase = get(),
            resendVerificationCodeUseCase = get(),
            validateVerificationCodeUseCase = get(),
        )
    }
}
