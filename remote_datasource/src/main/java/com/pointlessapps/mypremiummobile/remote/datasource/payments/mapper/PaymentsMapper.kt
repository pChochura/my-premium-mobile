package com.pointlessapps.mypremiummobile.remote.datasource.payments.mapper

import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.payments.dto.BalanceResponseDto as RemoteBalanceResponseDto

internal fun RemoteBalanceResponseDto.toBalanceResponseDto() = BalanceResponseDto(
    balance = balance,
    individualBankAccountNumber = individualBankAccountNumber,
    billingPeriod = billingPeriod,
)
