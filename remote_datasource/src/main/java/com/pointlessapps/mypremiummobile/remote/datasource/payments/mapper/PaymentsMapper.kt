package com.pointlessapps.mypremiummobile.remote.datasource.payments.mapper

import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.DeliveryMethodResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.InvoiceResponse
import com.pointlessapps.mypremiummobile.remote.datasource.payments.dto.BalanceResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.payments.dto.DeliveryMethodResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.payments.dto.InvoiceResponseDto
import java.text.SimpleDateFormat

internal fun BalanceResponseDto.toBalanceResponse() = BalanceResponse(
    balance = balance,
    individualBankAccountNumber = individualBankAccountNumber,
    billingPeriod = billingPeriod,
)

internal fun List<InvoiceResponseDto>.toInvoicesResponse(dateParser: SimpleDateFormat) = map {
    InvoiceResponse(
        invoiceNumber = it.invoiceNumber,
        invoiceDate = requireNotNull(dateParser.parse(it.invoiceDate)),
        paymentDate = requireNotNull(dateParser.parse(it.paymentDate)),
        amount = it.amount,
        status = it.status.lowercase().toBooleanStrict(),
        paymentDeadlineExceeded = it.paymentDeadlineExceeded,
    )
}

internal fun List<DeliveryMethodResponseDto>.toDeliveryMethods() = map {
    DeliveryMethodResponse(
        id = it.id,
        method = it.method,
        price = it.price,
        status = it.status,
        text = it.text,
    )
}
