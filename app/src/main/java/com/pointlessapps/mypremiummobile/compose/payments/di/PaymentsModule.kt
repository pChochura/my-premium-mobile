package com.pointlessapps.mypremiummobile.compose.payments.di

import com.pointlessapps.mypremiummobile.compose.payments.ui.PaymentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val paymentsModule = module {
    viewModel {
        PaymentsViewModel(
            errorHandler = get(),
            getUserNameUseCase = get(),
            getUserPhoneNumbersUseCase = get(),
            getPaymentAmountUseCase = get(),
            getInvoicesUseCase = get(),
            downloadInvoiceUseCase = get(),
            downloadBillingUseCase = get(),
            getPayWithPayUUrlUseCase = get(),
            dateFormatter = get(),
        )
    }
}
