package com.pointlessapps.mypremiummobile.datasource.payments

import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.InvoiceResponse
import java.util.*

interface PaymentsDatasource {
    suspend fun getBalance(): BalanceResponse

    suspend fun getPaymentAmount(): Float

    suspend fun getInvoices(fromDate: Date, toDate: Date): List<InvoiceResponse>
}
