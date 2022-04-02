package com.pointlessapps.mypremiummobile.domain.validation.di

import com.pointlessapps.mypremiummobile.domain.validation.ValidationRepository
import com.pointlessapps.mypremiummobile.domain.validation.ValidationRepositoryImpl
import com.pointlessapps.mypremiummobile.domain.validation.usecase.ValidateSimpleInputUseCase
import org.koin.dsl.module

internal val validationModule = module {
    single<ValidationRepository> {
        ValidationRepositoryImpl()
    }

    factory {
        ValidateSimpleInputUseCase(
            repository = get(),
        )
    }
}
