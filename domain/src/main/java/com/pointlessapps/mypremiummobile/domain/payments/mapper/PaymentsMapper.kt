package com.pointlessapps.mypremiummobile.domain.payments.mapper

import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponseDto
import com.pointlessapps.mypremiummobile.domain.payments.model.Balance

internal fun BalanceResponseDto.toBalance() = Balance(
    balance = balance,
    bankAccountNumber = individualBankAccountNumber,
    billingPeriod = billingPeriod,
)
