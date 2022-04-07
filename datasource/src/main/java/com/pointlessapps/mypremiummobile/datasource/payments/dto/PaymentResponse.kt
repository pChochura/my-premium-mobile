package com.pointlessapps.mypremiummobile.datasource.payments.dto

import java.util.*

data class PaymentResponse(
    val paymentDate: Date,
    val postingDate: Date,
    val amount: Float,
    val title: String,
)
