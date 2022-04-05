package com.pointlessapps.mypremiummobile.compose.payments.di

import com.pointlessapps.mypremiummobile.compose.payments.ui.PaymentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val paymentsModule = module {
    viewModel {
        PaymentsViewModel(
            errorHandler = get(),
            getPaymentsModelUseCase = get(),
            downloadInvoiceUseCase = get(),
            downloadBillingUseCase = get(),
            getPayWithPayUUrlUseCase = get(),
            changeDeliveryMethodUseCase = get(),
            getDeliveryMethodsUseCase = get(),
            dateFormatter = get(),
            numberFormatter = get(),
        )
    }
}
