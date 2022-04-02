package com.pointlessapps.mypremiummobile.domain.payments.di

import com.pointlessapps.mypremiummobile.domain.payments.PaymentRepositoryImpl
import com.pointlessapps.mypremiummobile.domain.payments.PaymentsRepository
import com.pointlessapps.mypremiummobile.domain.payments.usecase.GetBalanceUseCase
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
}
