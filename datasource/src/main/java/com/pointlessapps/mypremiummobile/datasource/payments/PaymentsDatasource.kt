package com.pointlessapps.mypremiummobile.datasource.payments

import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponseDto

interface PaymentsDatasource {
    suspend fun getBalance(): BalanceResponseDto
}
