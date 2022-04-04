package com.pointlessapps.mypremiummobile.domain.payments.usecase

import com.pointlessapps.mypremiummobile.domain.payments.PaymentsRepository

class GetPaymentAmountUseCase(
    private val paymentsRepository: PaymentsRepository,
) {

    fun prepare() = paymentsRepository.getPaymentAmount()
}
