package com.pointlessapps.mypremiummobile.domain.services.usecase

import com.pointlessapps.mypremiummobile.domain.services.ServicesRepository

class BuyInternetPackageUseCase(
    private val servicesRepository: ServicesRepository,
) {

    operator fun invoke(phoneNumberId: String, packageId: Int) =
        servicesRepository.buyInternetPackage(phoneNumberId, packageId)
}
