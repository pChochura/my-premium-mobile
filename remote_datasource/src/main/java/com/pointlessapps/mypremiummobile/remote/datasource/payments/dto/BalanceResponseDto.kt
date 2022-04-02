package com.pointlessapps.mypremiummobile.remote.datasource.payments.dto

data class BalanceResponseDto(
    val balance: String,
    val individualBankAccountNumber: String,
    val billingPeriod: String,
)
