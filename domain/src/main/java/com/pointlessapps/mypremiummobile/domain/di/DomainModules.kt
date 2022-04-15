package com.pointlessapps.mypremiummobile.domain.di

import com.pointlessapps.mypremiummobile.domain.auth.di.authModule
import com.pointlessapps.mypremiummobile.domain.payments.di.paymentsModule
import com.pointlessapps.mypremiummobile.domain.services.di.servicesModule
import com.pointlessapps.mypremiummobile.domain.settings.di.settingsModule
import com.pointlessapps.mypremiummobile.domain.utils.di.utilsModule
import com.pointlessapps.mypremiummobile.domain.validation.di.validationModule

val domainModules = listOf(
    domainModule,
    authModule,
    validationModule,
    paymentsModule,
    servicesModule,
    utilsModule,
    settingsModule,
)
