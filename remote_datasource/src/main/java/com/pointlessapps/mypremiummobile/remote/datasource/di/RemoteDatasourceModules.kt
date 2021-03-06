package com.pointlessapps.mypremiummobile.remote.datasource.di

import com.pointlessapps.mypremiummobile.remote.datasource.auth.di.authModule
import com.pointlessapps.mypremiummobile.remote.datasource.payments.di.paymentsModule
import com.pointlessapps.mypremiummobile.remote.datasource.services.di.servicesModule
import com.pointlessapps.mypremiummobile.remote.datasource.settings.di.settingsModule

val remoteDatasourceModules = listOf(
    authModule,
    paymentsModule,
    servicesModule,
    settingsModule,
)
