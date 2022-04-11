package com.pointlessapps.mypremiummobile.local.datasource.auth.di

import android.content.Context
import com.google.gson.Gson
import com.pointlessapps.mypremiummobile.datasource.auth.CredentialsStore
import com.pointlessapps.mypremiummobile.local.datasource.auth.CredentialsStoreImpl
import org.koin.dsl.module

internal val authModule = module {
    single<CredentialsStore> {
        CredentialsStoreImpl(
            gson = Gson(),
            sharedPreferences = get<Context>().getSharedPreferences(
                CredentialsStoreImpl.SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE,
            ),
            keyAlias = get<Context>().let {
                it.applicationInfo.loadLabel(it.packageManager)
            }.toString(),
        )
    }
}
