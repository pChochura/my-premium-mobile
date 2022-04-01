package com.pointlessapps.mypremiummobile

import android.app.Application
import com.pointlessapps.mypremiummobile.di.applicationModules
import com.pointlessapps.mypremiummobile.domain.di.domainModules
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyPremiumMobileApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@MyPremiumMobileApp)
            modules(
                applicationModules +
                        domainModules,
            )
        }
    }
}
