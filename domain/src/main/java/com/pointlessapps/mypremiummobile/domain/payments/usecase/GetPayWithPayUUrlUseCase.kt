package com.pointlessapps.mypremiummobile.domain.payments.usecase

import com.pointlessapps.mypremiummobile.domain.payments.PaymentsRepository

class GetPayWithPayUUrlUseCase(
    private val paymentsRepository: PaymentsRepository,
) {

    operator fun invoke(amount: Float) = paymentsRepository.getPayWithPayUUrl(amount)
}
