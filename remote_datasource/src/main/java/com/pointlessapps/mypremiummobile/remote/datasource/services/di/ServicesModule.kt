package com.pointlessapps.mypremiummobile.remote.datasource.services.di

import com.pointlessapps.mypremiummobile.datasource.services.ServicesDatasource
import com.pointlessapps.mypremiummobile.remote.datasource.services.ServicesDatasourceImpl
import com.pointlessapps.mypremiummobile.remote.datasource.services.service.ServicesService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

internal val servicesModule = module {
    single<ServicesDatasource> {
        ServicesDatasourceImpl(
            service = get(),
        )
    }

    single<ServicesService> {
        get<Retrofit>().create()
    }
}
