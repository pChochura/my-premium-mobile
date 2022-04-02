package com.pointlessapps.mypremiummobile.http.logger

import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

internal class TimberLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Timber.tag(TAG).d(message)
    }

    private companion object {
        private const val TAG = "AUDI_D_APP_HTTP"
    }
}
