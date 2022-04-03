package com.pointlessapps.mypremiummobile.di

import com.pointlessapps.mypremiummobile.MainViewModel
import com.pointlessapps.mypremiummobile.utils.errors.ErrorHandler
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val applicationModule = module {
    single { ErrorHandler() }

    viewModel {
        MainViewModel(
            isLoggedInUseCase = get(),
        )
    }
}
