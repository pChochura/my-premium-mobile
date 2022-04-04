package com.pointlessapps.mypremiummobile.datasource.payments.dto

import java.util.*

data class InvoiceResponse(
    val invoiceNumber: String,
    val invoiceDate: Date,
    val paymentDate: Date,
    val amount: Float,
    val status: Boolean,
    val paymentDeadlineExceeded: Boolean,
)
