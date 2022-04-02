package com.pointlessapps.mypremiummobile.utils.logger

import org.koin.core.logger.KOIN_TAG
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

internal class TimberKoinLogger(level: Level = Level.ERROR) : Logger(level) {
    override fun log(level: Level, msg: MESSAGE) {
        when (level) {
            Level.DEBUG -> Timber.tag(KOIN_TAG).d(msg)
            Level.INFO -> Timber.tag(KOIN_TAG).i(msg)
            Level.ERROR -> Timber.tag(KOIN_TAG).e(msg)
            Level.NONE -> Timber.tag(KOIN_TAG).e(msg)
        }
    }
}
