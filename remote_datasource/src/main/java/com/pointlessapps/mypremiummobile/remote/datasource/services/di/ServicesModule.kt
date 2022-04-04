package com.pointlessapps.mypremiummobile.remote.datasource.services.di

import com.pointlessapps.mypremiummobile.datasource.services.ServicesDatasource
import com.pointlessapps.mypremiummobile.remote.datasource.services.DATE_FORMAT
import com.pointlessapps.mypremiummobile.remote.datasource.services.ServicesDatasourceImpl
import com.pointlessapps.mypremiummobile.remote.datasource.services.service.ServicesService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create
import java.text.SimpleDateFormat
import java.util.*

private val SERVICES_DATE_FORMATTER = named("servicesDateFormatter")

internal val servicesModule = module {
    single(SERVICES_DATE_FORMATTER) { SimpleDateFormat(DATE_FORMAT, Locale.getDefault()) }

    single<ServicesDatasource> {
        ServicesDatasourceImpl(
            service = get(),
            dateParser = get(SERVICES_DATE_FORMATTER),
        )
    }

    single<ServicesService> {
        get<Retrofit>().create()
    }
}
