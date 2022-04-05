package com.pointlessapps.mypremiummobile.domain.auth.usecase

import com.pointlessapps.mypremiummobile.domain.auth.AuthRepository

class LoginUseCase(
    private val authRepository: AuthRepository,
) {

    operator fun invoke(login: String, password: String) = authRepository.login(login, password)
}
