package com.pointlessapps.mypremiummobile.domain.payments

import com.pointlessapps.mypremiummobile.datasource.payments.PaymentsDatasource
import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface PaymentsRepository {

    fun getBalance(): Flow<BalanceResponse>
}

internal class PaymentRepositoryImpl(
    private val paymentsDatasource: PaymentsDatasource,
) : PaymentsRepository {

    override fun getBalance(): Flow<BalanceResponse> = flow {
        emit(paymentsDatasource.getBalance())
    }.flowOn(Dispatchers.IO)
}
