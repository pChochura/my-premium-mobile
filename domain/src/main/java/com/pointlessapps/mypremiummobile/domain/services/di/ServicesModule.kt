package com.pointlessapps.mypremiummobile.domain.services.di

import com.pointlessapps.mypremiummobile.domain.services.ServicesRepository
import com.pointlessapps.mypremiummobile.domain.services.ServicesRepositoryImpl
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetUserPhoneNumbersUseCase
import org.koin.dsl.module

internal val servicesModule = module {
    single<ServicesRepository> {
        ServicesRepositoryImpl(
            servicesDatasource = get(),
        )
    }

    factory {
        GetUserPhoneNumbersUseCase(
            servicesRepository = get(),
        )
    }
}
