package com.pointlessapps.mypremiummobile.http.di

import android.content.Context
import com.google.gson.Gson
import com.pointlessapps.mypremiummobile.http.BASE_URL_PROPERTY_KEY
import com.pointlessapps.mypremiummobile.http.LOG_LEVEL_PROPERTY_KEY
import com.pointlessapps.mypremiummobile.http.authorization.AuthorizationInterceptor
import com.pointlessapps.mypremiummobile.http.authorization.SessionCookieJar
import com.pointlessapps.mypremiummobile.http.authorization.SessionCookieJar.Companion.SHARED_PREFERENCES_KEY
import com.pointlessapps.mypremiummobile.http.errors.ErrorCallAdapterFactory
import com.pointlessapps.mypremiummobile.http.logger.TimberLogger
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.error.MissingPropertyException
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

val httpModule = module {
    single {
        val logLevel = try {
            HttpLoggingInterceptor.Level.valueOf(getProperty(LOG_LEVEL_PROPERTY_KEY))
        } catch (exception: MissingPropertyException) {
            Timber.e(exception)
            HttpLoggingInterceptor.Level.NONE
        } catch (exception: IllegalArgumentException) {
            Timber.e(exception)
            HttpLoggingInterceptor.Level.NONE
        }

        HttpLoggingInterceptor(TimberLogger()).apply {
            level = logLevel
        }
    }

    single<CookieJar> {
        SessionCookieJar(
            gson = Gson(),
            sharedPreferences = get<Context>().getSharedPreferences(
                SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE,
            ),
        )
    }

    single {
        AuthorizationInterceptor(
            authorizationTokenStore = get(),
            gson = Gson(),
        )
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<AuthorizationInterceptor>())
            .cookieJar(get())
            .build()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create(Gson())
    }

    single<CallAdapter.Factory> {
        ErrorCallAdapterFactory(Gson())
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(getProperty<String>(BASE_URL_PROPERTY_KEY))
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .build()
    }
}
