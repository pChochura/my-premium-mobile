package com.pointlessapps.mypremiummobile.domain.model

import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.DeliveryMethodResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.InvoiceResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.PaymentResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.PhoneNumberResponse

data class PaymentsModel(
    val phoneNumber: PhoneNumberResponse,
    val userInfo: UserInfoResponse,
    val paymentAmount: Float,
    val invoices: List<InvoiceResponse>,
    val payments: List<PaymentResponse>,
    val deliveryMethods: List<DeliveryMethodResponse>,
)
