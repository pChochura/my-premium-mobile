package com.pointlessapps.mypremiummobile.domain.services.usecase

import com.pointlessapps.mypremiummobile.domain.services.ServicesRepository

class GetInternetPackagesUseCase(
    private val servicesRepository: ServicesRepository,
) {

    operator fun invoke(phoneNumberId: String) =
        servicesRepository.getInternetPackages(phoneNumberId)
}
