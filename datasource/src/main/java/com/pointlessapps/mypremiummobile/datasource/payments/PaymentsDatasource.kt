package com.pointlessapps.mypremiummobile.datasource.payments

import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.DeliveryMethodResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.InvoiceResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.PaymentResponse
import java.util.*

interface PaymentsDatasource {
    suspend fun getBalance(): BalanceResponse

    suspend fun getPaymentAmount(): Float

    suspend fun getInvoices(fromDate: Date, toDate: Date): List<InvoiceResponse>

    suspend fun getPayments(fromDate: Date, toDate: Date): List<PaymentResponse>

    suspend fun getDeliveryMethods(): List<DeliveryMethodResponse>

    suspend fun changeDeliveryMethod(deliveryMethodId: Int, state: Boolean)

    suspend fun downloadInvoice(invoiceNumber: String): String

    suspend fun downloadBilling(invoiceNumber: String): String

    suspend fun getPayWithPayUUrl(amount: Float): String
}
