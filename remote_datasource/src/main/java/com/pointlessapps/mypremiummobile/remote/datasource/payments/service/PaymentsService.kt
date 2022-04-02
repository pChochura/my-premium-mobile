package com.pointlessapps.mypremiummobile.remote.datasource.payments.service

import com.pointlessapps.mypremiummobile.http.authorization.Authorize
import com.pointlessapps.mypremiummobile.remote.datasource.payments.dto.BalanceResponseDto
import retrofit2.http.GET

internal interface PaymentsService {

    @Authorize
    @GET("payments/getCustomerBalance")
    suspend fun getBalance(): BalanceResponseDto
}
