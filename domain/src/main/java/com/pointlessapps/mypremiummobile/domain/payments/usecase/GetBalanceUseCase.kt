package com.pointlessapps.mypremiummobile.domain.payments.usecase

import com.pointlessapps.mypremiummobile.domain.payments.PaymentsRepository

class GetBalanceUseCase(
    private val paymentsRepository: PaymentsRepository,
) {

    fun prepare() = paymentsRepository.getBalance()
}
