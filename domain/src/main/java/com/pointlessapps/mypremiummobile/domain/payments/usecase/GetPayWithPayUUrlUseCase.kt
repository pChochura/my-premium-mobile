package com.pointlessapps.mypremiummobile.domain.payments.usecase

import com.pointlessapps.mypremiummobile.domain.payments.PaymentsRepository

class GetPayWithPayUUrlUseCase(
    private val paymentsRepository: PaymentsRepository,
) {

    fun prepare(amount: Float) = paymentsRepository.getPayWithPayUUrl(amount)
}
