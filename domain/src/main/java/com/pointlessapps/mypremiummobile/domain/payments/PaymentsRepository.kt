package com.pointlessapps.mypremiummobile.domain.payments

import com.pointlessapps.mypremiummobile.datasource.payments.PaymentsDatasource
import com.pointlessapps.mypremiummobile.domain.payments.mapper.toBalance
import com.pointlessapps.mypremiummobile.domain.payments.model.Balance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface PaymentsRepository {

    fun getBalance(): Flow<Balance>
}

internal class PaymentRepositoryImpl(
    private val paymentsDatasource: PaymentsDatasource,
) : PaymentsRepository {

    override fun getBalance(): Flow<Balance> = flow {
        emit(paymentsDatasource.getBalance().toBalance())
    }.flowOn(Dispatchers.IO)
}
