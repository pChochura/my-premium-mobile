package com.pointlessapps.mypremiummobile.domain.auth.di

import com.pointlessapps.mypremiummobile.domain.auth.AuthRepository
import com.pointlessapps.mypremiummobile.domain.auth.AuthRepositoryImpl
import com.pointlessapps.mypremiummobile.domain.auth.usecase.LoginUseCase
import org.koin.dsl.module

internal val authModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            authDatasource = get(),
        )
    }

    factory {
        LoginUseCase(
            authRepository = get(),
        )
    }
}
