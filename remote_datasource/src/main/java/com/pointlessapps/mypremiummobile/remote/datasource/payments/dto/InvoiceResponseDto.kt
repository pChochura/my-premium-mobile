package com.pointlessapps.mypremiummobile.remote.datasource.payments.dto

import androidx.annotation.Keep

@Keep
data class InvoiceResponseDto(
    val invoiceNumber: String,
    val invoiceDate: String,
    val paymentDate: String,
    val amount: Float,
    val status: String,
    val paymentDeadlineExceeded: Boolean,
)
