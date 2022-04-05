package com.pointlessapps.mypremiummobile.di

import com.pointlessapps.mypremiummobile.compose.dashboard.di.dashboardModule
import com.pointlessapps.mypremiummobile.compose.login.di.loginModule
import com.pointlessapps.mypremiummobile.compose.login.two.factor.di.loginTwoFactorModule
import com.pointlessapps.mypremiummobile.compose.payments.di.paymentsModule

val applicationModules = listOf(
    applicationModule,
    loginModule,
    loginTwoFactorModule,
    dashboardModule,
    paymentsModule,
)
