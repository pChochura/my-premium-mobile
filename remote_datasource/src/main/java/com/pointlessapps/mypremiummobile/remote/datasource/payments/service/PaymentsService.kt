package com.pointlessapps.mypremiummobile.remote.datasource.payments.service

import com.pointlessapps.mypremiummobile.http.authorization.Authorize
import com.pointlessapps.mypremiummobile.remote.datasource.payments.dto.BalanceResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.payments.dto.InvoiceResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.payments.dto.InvoicesBodyDto
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface PaymentsService {

    @Authorize
    @GET("payments/getCustomerBalance")
    suspend fun getBalance(): BalanceResponseDto

    @Authorize
    @GET("payments/getPaymentAmount")
    suspend fun getPaymentAmount(): Float

    @Authorize
    @POST("payments/getInvoices")
    suspend fun getInvoices(
        @Body invoicesBodyDto: InvoicesBodyDto,
    ): List<InvoiceResponseDto>

    @Authorize
    @POST("payments/getInvoiceDocument")
    suspend fun getInvoiceDocument(
        @Body body: RequestBody,
    ): Response<ResponseBody>

    @Authorize
    @POST("payments/getBillingDocument")
    suspend fun getBillingDocument(
        @Body body: RequestBody,
    ): Response<ResponseBody>
}
