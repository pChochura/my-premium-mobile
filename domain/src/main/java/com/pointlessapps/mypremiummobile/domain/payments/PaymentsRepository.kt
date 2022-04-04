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
}
