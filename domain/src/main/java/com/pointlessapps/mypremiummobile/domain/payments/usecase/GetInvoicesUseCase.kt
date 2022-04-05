package com.pointlessapps.mypremiummobile.domain.payments.usecase

import com.pointlessapps.mypremiummobile.domain.payments.PaymentsRepository
import java.util.*

class GetInvoicesUseCase(
    private val paymentsRepository: PaymentsRepository,
) {

    operator fun invoke(fromDate: Date, toDate: Date) =
        paymentsRepository.getInvoices(fromDate, toDate)
}
