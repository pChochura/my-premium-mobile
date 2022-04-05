package com.pointlessapps.mypremiummobile.domain.usecase

import com.pointlessapps.mypremiummobile.domain.auth.usecase.GetUserNameUseCase
import com.pointlessapps.mypremiummobile.domain.model.PaymentsModel
import com.pointlessapps.mypremiummobile.domain.payments.usecase.GetInvoicesUseCase
import com.pointlessapps.mypremiummobile.domain.payments.usecase.GetPaymentAmountUseCase
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetUserPhoneNumbersUseCase
import kotlinx.coroutines.flow.combine
import java.util.*

class GetPaymentsModelUseCase(
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getUserPhoneNumbersUseCase: GetUserPhoneNumbersUseCase,
    private val getPaymentAmountUseCase: GetPaymentAmountUseCase,
    private val getInvoicesUseCase: GetInvoicesUseCase,
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
    ) { phoneNumbers, userInfo, paymentAmount, invoices ->
        val phoneNumber = requireNotNull(phoneNumbers.find { it.isMain })

        return@combine PaymentsModel(
            phoneNumber = phoneNumber,
            userInfo = userInfo,
            paymentAmount = paymentAmount,
            invoices = invoices,
        )
    }
}
