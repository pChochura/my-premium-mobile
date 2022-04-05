package com.pointlessapps.mypremiummobile.domain.payments.usecase

import com.pointlessapps.mypremiummobile.domain.payments.PaymentsRepository

class ChangeDeliveryMethodUseCase(
    private val paymentsRepository: PaymentsRepository,
) {

    operator fun invoke(deliveryMethodId: Int, state: Boolean) =
        paymentsRepository.changeDeliveryMethod(deliveryMethodId, state)
}
