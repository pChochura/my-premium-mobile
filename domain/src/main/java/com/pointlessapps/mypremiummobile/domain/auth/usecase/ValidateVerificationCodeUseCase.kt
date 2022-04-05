package com.pointlessapps.mypremiummobile.domain.auth.usecase

import com.pointlessapps.mypremiummobile.domain.auth.AuthRepository

class ValidateVerificationCodeUseCase(
    private val authRepository: AuthRepository,
) {

    operator fun invoke(code: String) = authRepository.validateCode(code)
}
