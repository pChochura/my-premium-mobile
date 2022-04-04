package com.pointlessapps.mypremiummobile.domain.payments.di

import com.pointlessapps.mypremiummobile.domain.payments.PaymentRepositoryImpl
import com.pointlessapps.mypremiummobile.domain.payments.PaymentsRepository
import com.pointlessapps.mypremiummobile.domain.payments.usecase.*
import org.koin.dsl.module

internal val paymentsModule = module {
    single<PaymentsRepository> {
        PaymentRepositoryImpl(
            paymentsDatasource = get(),
        )
    }

    factory {
        GetBalanceUseCase(
            paymentsRepository = get(),
        )
    }

    factory {
        GetPaymentAmountUseCase(
            paymentsRepository = get(),
        )
    }

    factory {
        GetInvoicesUseCase(
            paymentsRepository = get(),
        )
    }

    factory {
        DownloadInvoiceUseCase(
            paymentsRepository = get(),
        )
    }

    factory {
        DownloadBillingUseCase(
            paymentsRepository = get(),
        )
    }

    factory {
        GetPayWithPayUUrlUseCase(
            paymentsRepository = get(),
        )
    }
}
