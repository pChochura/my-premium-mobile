package com.pointlessapps.mypremiummobile.remote.datasource.payments

import com.pointlessapps.mypremiummobile.datasource.payments.PaymentsDatasource
import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponse
import com.pointlessapps.mypremiummobile.remote.datasource.payments.mapper.toBalanceResponse
import com.pointlessapps.mypremiummobile.remote.datasource.payments.service.PaymentsService

internal class PaymentsDatasourceImpl(
    private val paymentsService: PaymentsService,
) : PaymentsDatasource {

    override suspend fun getBalance(): BalanceResponse =
        paymentsService.getBalance().toBalanceResponse()
}
