package com.pointlessapps.mypremiummobile.remote.datasource.payments.di

import com.pointlessapps.mypremiummobile.datasource.payments.PaymentsDatasource
import com.pointlessapps.mypremiummobile.remote.datasource.payments.DATE_FORMAT
import com.pointlessapps.mypremiummobile.remote.datasource.payments.PaymentsDatasourceImpl
import com.pointlessapps.mypremiummobile.remote.datasource.payments.service.PaymentsService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create
import java.text.SimpleDateFormat
import java.util.*

private val PAYMENTS_DATE_FORMATTER = named("paymentsDateFormatter")

internal val paymentsModule = module {
    single(PAYMENTS_DATE_FORMATTER) { SimpleDateFormat(DATE_FORMAT, Locale.getDefault()) }

    single<PaymentsDatasource> {
        PaymentsDatasourceImpl(
            context = get(),
            paymentsService = get(),
            dateParser = get(PAYMENTS_DATE_FORMATTER),
        )
    }

    single<PaymentsService> {
        get<Retrofit>().create()
    }
}
