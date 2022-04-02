package com.pointlessapps.mypremiummobile.domain.payments.model

data class Balance(
    val balance: String,
    val bankAccountNumber: String,
    val billingPeriod: String,
)
