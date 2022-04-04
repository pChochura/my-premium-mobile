package com.pointlessapps.mypremiummobile.domain.utils.di

import com.pointlessapps.mypremiummobile.domain.utils.DATE_FORMAT
import com.pointlessapps.mypremiummobile.domain.utils.DATE_TIME_FORMAT
import com.pointlessapps.mypremiummobile.domain.utils.DateFormatter
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.*

internal val utilsModule = module {
    single(named(DATE_FORMAT)) { SimpleDateFormat(DATE_FORMAT, Locale.getDefault()) }
    single(named(DATE_TIME_FORMAT)) { SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault()) }

    single {
        DateFormatter(
            dateFormat = get(named(DATE_FORMAT)),
            dateTimeFormat = get(named(DATE_TIME_FORMAT)),
        )
    }
}
