package com.pointlessapps.mypremiummobile.domain.services.usecase

import com.pointlessapps.mypremiummobile.domain.services.ServicesRepository

class GetUserPhoneNumbersUseCase(
    private val servicesRepository: ServicesRepository,
) {

    operator fun invoke() = servicesRepository.getUserPhoneNumbers()
}
