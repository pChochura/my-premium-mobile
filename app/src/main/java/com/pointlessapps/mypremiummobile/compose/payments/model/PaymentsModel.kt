package com.pointlessapps.mypremiummobile.compose.payments.model

import android.os.Parcelable
import com.pointlessapps.mypremiummobile.compose.model.Balance
import com.pointlessapps.mypremiummobile.compose.model.UserInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentsModel(
    val userInfo: UserInfo,
    val balance: Balance,
    val invoices: List<Invoice>,
) : Parcelable
