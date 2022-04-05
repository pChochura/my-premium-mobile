package com.pointlessapps.mypremiummobile.domain.auth.usecase

import com.pointlessapps.mypremiummobile.domain.auth.AuthRepository

class SendVerificationCodeUseCase(
    private val authRepository: AuthRepository,
) {

    operator fun invoke() = authRepository.sendVerificationCode()
}
