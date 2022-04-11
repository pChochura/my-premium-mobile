package com.pointlessapps.mypremiummobile.domain.auth.di

import com.pointlessapps.mypremiummobile.domain.auth.AuthRepository
import com.pointlessapps.mypremiummobile.domain.auth.AuthRepositoryImpl
import com.pointlessapps.mypremiummobile.domain.auth.usecase.*
import org.koin.dsl.module

internal val authModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            authDatasource = get(),
            userInfoDatasource = get(),
        )
    }

    factory {
        LoginUseCase(
            authRepository = get(),
        )
    }

    factory {
        IsLoggedInUseCase(
            authRepository = get(),
        )
    }

    factory {
        GetUserNameUseCase(
            authRepository = get(),
        )
    }

    factory {
        SendVerificationCodeUseCase(
            authRepository = get(),
        )
    }

    factory {
        ResendVerificationCodeUseCase(
            authRepository = get(),
        )
    }

    factory {
        ValidateVerificationCodeUseCase(
            authRepository = get(),
        )
    }

    factory {
        SaveCredentialsUseCase(
            authRepository = get(),
        )
    }

    factory {
        GetCredentialsUseCase(
            authRepository = get(),
        )
    }
}
