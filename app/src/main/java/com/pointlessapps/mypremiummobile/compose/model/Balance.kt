package com.pointlessapps.mypremiummobile.compose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Balance(
    val balance: String = "",
    val billingPeriod: String = "",
    val accountNumber: String = "",
) : Parcelable
