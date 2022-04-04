package com.pointlessapps.mypremiummobile.domain.payments.usecase

import com.pointlessapps.mypremiummobile.domain.payments.PaymentsRepository

class DownloadBillingUseCase(
    private val paymentsRepository: PaymentsRepository,
) {

    fun prepare(invoiceNumber: String) = paymentsRepository.downloadBilling(invoiceNumber)
}
