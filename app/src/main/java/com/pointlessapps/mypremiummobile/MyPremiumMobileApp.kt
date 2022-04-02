package com.pointlessapps.mypremiummobile

import android.app.Application
import com.pointlessapps.mypremiummobile.di.applicationModules
import com.pointlessapps.mypremiummobile.domain.di.domainModules
import com.pointlessapps.mypremiummobile.http.HTTP_PROPERTIES_FILE
import com.pointlessapps.mypremiummobile.http.di.httpModule
import com.pointlessapps.mypremiummobile.remote.datasource.di.remoteDatasourceModules
import com.pointlessapps.mypremiummobile.utils.logger.TimberKoinLogger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin
import timber.log.Timber

class MyPremiumMobileApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            logger(TimberKoinLogger())
            androidContext(this@MyPremiumMobileApp)
            androidFileProperties(HTTP_PROPERTIES_FILE)
            modules(
                applicationModules +
                        httpModule +
                        domainModules +
                        remoteDatasourceModules,
            )
        }
    }
}
