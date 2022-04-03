package com.pointlessapps.mypremiummobile.remote.datasource.payments.mapper

import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponse
import com.pointlessapps.mypremiummobile.remote.datasource.payments.dto.BalanceResponseDto as RemoteBalanceResponseDto

internal fun RemoteBalanceResponseDto.toBalanceResponse() = BalanceResponse(
    balance = balance,
    individualBankAccountNumber = individualBankAccountNumber,
    billingPeriod = billingPeriod,
)
