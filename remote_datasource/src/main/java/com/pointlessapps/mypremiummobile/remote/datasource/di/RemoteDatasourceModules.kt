package com.pointlessapps.mypremiummobile.remote.datasource.di

import com.pointlessapps.mypremiummobile.remote.datasource.auth.di.authModule
import com.pointlessapps.mypremiummobile.remote.datasource.payments.di.paymentsModule

val remoteDatasourceModules = listOf(
    authModule,
    paymentsModule,
)
