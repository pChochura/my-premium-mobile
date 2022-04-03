package com.pointlessapps.mypremiummobile.domain.services.usecase

import com.pointlessapps.mypremiummobile.domain.services.ServicesRepository

class GetUserPhoneNumbersUseCase(
    private val servicesRepository: ServicesRepository,
) {

    fun prepare() = servicesRepository.getUserPhoneNumbers()
}
