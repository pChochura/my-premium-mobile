package com.pointlessapps.mypremiummobile.domain.auth.usecase

import com.pointlessapps.mypremiummobile.domain.auth.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository,
) {

    operator fun invoke() = authRepository.logout()
}
