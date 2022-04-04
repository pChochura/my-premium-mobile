package com.pointlessapps.mypremiummobile.remote.datasource.payments

import com.pointlessapps.mypremiummobile.datasource.payments.PaymentsDatasource
import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.InvoiceResponse
import com.pointlessapps.mypremiummobile.remote.datasource.payments.dto.InvoicesBodyDto
import com.pointlessapps.mypremiummobile.remote.datasource.payments.mapper.toBalanceResponse
import com.pointlessapps.mypremiummobile.remote.datasource.payments.mapper.toInvoicesResponse
import com.pointlessapps.mypremiummobile.remote.datasource.payments.service.PaymentsService
import java.text.SimpleDateFormat
import java.util.*

internal class PaymentsDatasourceImpl(
    private val paymentsService: PaymentsService,
    private val dateParser: SimpleDateFormat,
) : PaymentsDatasource {

    override suspend fun getBalance(): BalanceResponse =
        paymentsService.getBalance().toBalanceResponse()

    override suspend fun getPaymentAmount(): Float =
        paymentsService.getPaymentAmount()

    override suspend fun getInvoices(fromDate: Date, toDate: Date): List<InvoiceResponse> =
        paymentsService.getInvoices(
            InvoicesBodyDto(
                startDate = dateParser.format(fromDate),
                endDate = dateParser.format(toDate),
            ),
        ).toInvoicesResponse(dateParser)
}
