package com.pointlessapps.mypremiummobile.domain.services.usecase

import com.pointlessapps.mypremiummobile.domain.services.ServicesRepository

class GetInternetPackageStatusUseCase(
    private val servicesRepository: ServicesRepository,
) {

    fun prepare(phoneNumberId: String) = servicesRepository.getInternetPackageStatus(phoneNumberId)
}
