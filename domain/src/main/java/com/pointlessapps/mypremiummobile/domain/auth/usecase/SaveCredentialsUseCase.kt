package com.pointlessapps.mypremiummobile.domain.auth.usecase

import com.pointlessapps.mypremiummobile.domain.auth.AuthRepository

class SaveCredentialsUseCase(
    private val authRepository: AuthRepository,
) {

    operator fun invoke(login: String, password: String) =
        authRepository.saveCredentials(login, password)
}
