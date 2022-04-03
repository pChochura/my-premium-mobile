package com.pointlessapps.mypremiummobile.datasource.payments

import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponse

interface PaymentsDatasource {
    suspend fun getBalance(): BalanceResponse
}
