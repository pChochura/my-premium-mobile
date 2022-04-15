package com.pointlessapps.mypremiummobile.domain.usecase

import com.pointlessapps.mypremiummobile.domain.auth.usecase.GetUserNameUseCase
import com.pointlessapps.mypremiummobile.domain.model.PaymentsModel
import com.pointlessapps.mypremiummobile.domain.payments.usecase.GetDeliveryMethodsUseCase
import com.pointlessapps.mypremiummobile.domain.payments.usecase.GetInvoicesUseCase
import com.pointlessapps.mypremiummobile.domain.payments.usecase.GetPaymentAmountUseCase
import com.pointlessapps.mypremiummobile.domain.payments.usecase.GetPaymentsUseCase
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetUserPhoneNumbersUseCase
import com.pointlessapps.mypremiummobile.domain.utils.combine
import java.util.*

class GetPaymentsModelUseCase(
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getUserPhoneNumbersUseCase: GetUserPhoneNumbersUseCase,
    private val getPaymentAmountUseCase: GetPaymentAmountUseCase,
    private val getInvoicesUseCase: GetInvoicesUseCase,
    private val getPaymentsUseCase: GetPaymentsUseCase,
    private val getDeliveryMethodsUseCase: GetDeliveryMethodsUseCase,
) {

    companion object {
        private const val THREE_MONTHS = 3
        private val today = Calendar.getInstance().time
        private val threeMonthsAgo = Calendar.getInstance().apply {
            add(Calendar.MONTH, -THREE_MONTHS)
        }.time
    }

    operator fun invoke() = combine(
        getUserPhoneNumbersUseCase(),
        getUserNameUseCase(),
        getPaymentAmountUseCase(),
        getInvoicesUseCase(threeMonthsAgo, today),
        getPaymentsUseCase(threeMonthsAgo, today),
        getDeliveryMethodsUseCase(),
    ) { phoneNumbers, userInfo, paymentAmount, invoices, payments, deliveryMethods ->
        val phoneNumber = requireNotNull(phoneNumbers.find { it.isMain })

        return@combine PaymentsModel(
            phoneNumber = phoneNumber,
            userInfo = userInfo,
            paymentAmount = paymentAmount,
            invoices = invoices,
            payments = payments,
            deliveryMethods = deliveryMethods,
        )
    }
}
