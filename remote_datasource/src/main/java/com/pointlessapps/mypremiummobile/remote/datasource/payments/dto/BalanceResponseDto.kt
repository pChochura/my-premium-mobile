package com.pointlessapps.mypremiummobile.remote.datasource.payments.dto

import androidx.annotation.Keep

@Keep
data class BalanceResponseDto(
    val balance: String,
    val individualBankAccountNumber: String,
    val billingPeriod: String,
)
