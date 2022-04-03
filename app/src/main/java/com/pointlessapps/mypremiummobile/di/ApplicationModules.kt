package com.pointlessapps.mypremiummobile.di

import com.pointlessapps.mypremiummobile.compose.dashboard.di.dashboardModule
import com.pointlessapps.mypremiummobile.compose.login.di.loginModule

val applicationModules = listOf(
    applicationModule,
    loginModule,
    dashboardModule,
)
