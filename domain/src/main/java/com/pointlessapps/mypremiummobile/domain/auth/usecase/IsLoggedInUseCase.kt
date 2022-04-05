package com.pointlessapps.mypremiummobile.domain.auth.usecase

import com.pointlessapps.mypremiummobile.domain.auth.AuthRepository

class IsLoggedInUseCase(
    private val authRepository: AuthRepository,
) {

    operator fun invoke() = authRepository.isLoggedIn()
}
