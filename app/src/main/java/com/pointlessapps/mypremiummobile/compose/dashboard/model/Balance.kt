package com.pointlessapps.mypremiummobile.compose.dashboard.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Balance(
    val balance: String = "",
    val billingPeriod: String = "",
    val accountNumber: String = "",
) : Parcelable
