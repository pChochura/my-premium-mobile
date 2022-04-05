package com.pointlessapps.mypremiummobile.domain.payments.usecase

import com.pointlessapps.mypremiummobile.domain.payments.PaymentsRepository

class DownloadBillingUseCase(
    private val paymentsRepository: PaymentsRepository,
) {

    operator fun invoke(invoiceNumber: String) = paymentsRepository.downloadBilling(invoiceNumber)
}
