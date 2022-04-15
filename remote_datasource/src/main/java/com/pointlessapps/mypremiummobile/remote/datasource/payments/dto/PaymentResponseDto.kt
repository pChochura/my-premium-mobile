package com.pointlessapps.mypremiummobile.remote.datasource.payments.dto

import androidx.annotation.Keep

@Keep
data class PaymentResponseDto(
    val paymentDate: String,
    val postingDate: String,
    val amount: Float,
    val title: String,
)
