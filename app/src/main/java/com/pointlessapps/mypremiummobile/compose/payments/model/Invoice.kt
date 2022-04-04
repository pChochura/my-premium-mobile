package com.pointlessapps.mypremiummobile.compose.payments.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Invoice(
    val invoiceNumber: String,
    val invoiceDate: String,
    val paymentDate: String,
    val amount: Float,
    val isPaid: Boolean,
) : Parcelable
