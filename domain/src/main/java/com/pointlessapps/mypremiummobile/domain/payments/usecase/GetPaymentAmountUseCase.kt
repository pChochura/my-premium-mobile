package com.pointlessapps.mypremiummobile.domain.payments.usecase

import com.pointlessapps.mypremiummobile.domain.payments.PaymentsRepository

class GetPaymentAmountUseCase(
    private val paymentsRepository: PaymentsRepository,
) {

    operator fun invoke() = paymentsRepository.getPaymentAmount()
}
