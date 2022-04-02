package com.pointlessapps.mypremiummobile.remote.datasource.auth.di

import com.pointlessapps.mypremiummobile.datasource.auth.AuthDatasource
import com.pointlessapps.mypremiummobile.remote.datasource.auth.AuthDatasourceImpl
import com.pointlessapps.mypremiummobile.remote.datasource.auth.service.AuthService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

internal val authModule = module {
    single<AuthDatasource> {
        AuthDatasourceImpl(
            authService = get(),
        )
    }

    single<AuthService> {
        get<Retrofit>().create()
    }
}
