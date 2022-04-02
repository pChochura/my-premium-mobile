package com.pointlessapps.mypremiummobile.remote.datasource.payments.di

import com.pointlessapps.mypremiummobile.datasource.payments.PaymentsDatasource
import com.pointlessapps.mypremiummobile.remote.datasource.payments.PaymentsDatasourceImpl
import com.pointlessapps.mypremiummobile.remote.datasource.payments.service.PaymentsService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

internal val paymentsModule = module {
    single<PaymentsDatasource> {
        PaymentsDatasourceImpl(
            paymentsService = get(),
        )
    }

    single<PaymentsService> {
        get<Retrofit>().create()
    }
}
