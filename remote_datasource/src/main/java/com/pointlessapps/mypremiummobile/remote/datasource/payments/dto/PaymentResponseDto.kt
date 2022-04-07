package com.pointlessapps.mypremiummobile.remote.datasource.payments.dto

data class PaymentResponseDto(
    val paymentDate: String,
    val postingDate: String,
    val amount: Float,
    val title: String,
)
