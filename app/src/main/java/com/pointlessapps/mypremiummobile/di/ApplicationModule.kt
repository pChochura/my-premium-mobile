package com.pointlessapps.mypremiummobile.di

import com.pointlessapps.mypremiummobile.utils.errors.ErrorHandler
import org.koin.dsl.module

internal val applicationModule = module {
    single { ErrorHandler() }
}
