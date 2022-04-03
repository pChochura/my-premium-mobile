package com.pointlessapps.mypremiummobile.remote.datasource.auth.di

import android.content.Context
import com.pointlessapps.mypremiummobile.datasource.auth.AuthDatasource
import com.pointlessapps.mypremiummobile.datasource.auth.AuthorizationTokenStore
import com.pointlessapps.mypremiummobile.datasource.auth.UserInfoDatasource
import com.pointlessapps.mypremiummobile.remote.datasource.auth.AuthDatasourceImpl
import com.pointlessapps.mypremiummobile.remote.datasource.auth.AuthorizationTokenStoreImpl
import com.pointlessapps.mypremiummobile.remote.datasource.auth.AuthorizationTokenStoreImpl.Companion.SHARED_PREFERENCES_KEY
import com.pointlessapps.mypremiummobile.remote.datasource.auth.UserInfoDatasourceImpl
import com.pointlessapps.mypremiummobile.remote.datasource.auth.service.AuthService
import com.pointlessapps.mypremiummobile.remote.datasource.auth.service.UserInfoService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

internal val authModule = module {
    single<AuthDatasource> {
        AuthDatasourceImpl(
            authorizationTokenStore = get(),
            authService = get(),
        )
    }

    single<UserInfoDatasource> {
        UserInfoDatasourceImpl(
            userInfoService = get(),
        )
    }

    single<AuthService> {
        get<Retrofit>().create()
    }

    single<UserInfoService> {
        get<Retrofit>().create()
    }

    single<AuthorizationTokenStore> {
        AuthorizationTokenStoreImpl(
            sharedPreferences = get<Context>().getSharedPreferences(
                SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE,
            ),
        )
    }
}
