package com.pointlessapps.mypremiummobile.datasource.payments.dto

data class BalanceResponse(
    val balance: String,
    val individualBankAccountNumber: String,
    val billingPeriod: String,
)
