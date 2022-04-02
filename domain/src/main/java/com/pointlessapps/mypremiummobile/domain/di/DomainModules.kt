package com.pointlessapps.mypremiummobile.domain.di

import com.pointlessapps.mypremiummobile.domain.auth.di.authModule
import com.pointlessapps.mypremiummobile.domain.validation.di.validationModule

val domainModules = listOf(
    authModule,
    validationModule,
)
