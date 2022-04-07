package com.pointlessapps.mypremiummobile.compose.payments.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Payment(
    val title: String,
    val amount: String,
    val paymentDate: String,
    val postingDate: String,
) : Parcelable
