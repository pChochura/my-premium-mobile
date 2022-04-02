package com.pointlessapps.mypremiummobile.remote.datasource.payments

import com.pointlessapps.mypremiummobile.datasource.payments.PaymentsDatasource
import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.payments.mapper.toBalanceResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.payments.service.PaymentsService

internal class PaymentsDatasourceImpl(
    private val paymentsService: PaymentsService,
) : PaymentsDatasource {

    override suspend fun getBalance(): BalanceResponseDto =
        paymentsService.getBalance().toBalanceResponseDto()
}
