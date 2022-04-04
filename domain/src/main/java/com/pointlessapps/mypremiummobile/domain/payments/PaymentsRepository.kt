package com.pointlessapps.mypremiummobile.domain.payments

import com.pointlessapps.mypremiummobile.datasource.payments.PaymentsDatasource
import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.InvoiceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*

interface PaymentsRepository {

    fun getBalance(): Flow<BalanceResponse>

    fun getPaymentAmount(): Flow<Float>

    fun getInvoices(fromDate: Date, toDate: Date): Flow<List<InvoiceResponse>>

    fun downloadInvoice(invoiceNumber: String): Flow<String>

    fun downloadBilling(invoiceNumber: String): Flow<String>
}

internal class PaymentRepositoryImpl(
    private val paymentsDatasource: PaymentsDatasource,
) : PaymentsRepository {

    override fun getBalance(): Flow<BalanceResponse> = flow {
        emit(paymentsDatasource.getBalance())
    }.flowOn(Dispatchers.IO)

    override fun getPaymentAmount(): Flow<Float> = flow {
        emit(paymentsDatasource.getPaymentAmount())
    }.flowOn(Dispatchers.IO)

    override fun getInvoices(fromDate: Date, toDate: Date): Flow<List<InvoiceResponse>> = flow {
        emit(paymentsDatasource.getInvoices(fromDate, toDate))
    }.flowOn(Dispatchers.IO)

    override fun downloadInvoice(invoiceNumber: String): Flow<String> = flow {
        emit(paymentsDatasource.downloadInvoice(invoiceNumber))
    }.flowOn(Dispatchers.IO)

    override fun downloadBilling(invoiceNumber: String): Flow<String> = flow {
        emit(paymentsDatasource.downloadBilling(invoiceNumber))
    }.flowOn(Dispatchers.IO)
}
