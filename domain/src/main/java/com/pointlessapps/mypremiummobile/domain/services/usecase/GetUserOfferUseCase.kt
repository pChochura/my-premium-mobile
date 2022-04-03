package com.pointlessapps.mypremiummobile.domain.services.usecase

import com.pointlessapps.mypremiummobile.domain.services.ServicesRepository

class GetUserOfferUseCase(
    private val servicesRepository: ServicesRepository,
) {

    fun prepare(phoneNumberId: String) = servicesRepository.getUserOffer(phoneNumberId)
}
