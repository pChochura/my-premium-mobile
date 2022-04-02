package com.pointlessapps.mypremiummobile.utils.errors

import androidx.annotation.StringRes
import com.pointlessapps.mypremiummobile.R
import com.pointlessapps.mypremiummobile.errors.AuthorizationInvalidUserCredentialsException

internal class ErrorHandler {

    @StringRes
    fun mapThrowableToErrorMessage(throwable: Throwable): Int =
        mapAuthorizationErrors(throwable) ?: R.string.something_went_wrong

    @StringRes
    private fun mapAuthorizationErrors(throwable: Throwable) = when (throwable) {
        is AuthorizationInvalidUserCredentialsException -> R.string.incorrect_login_or_password
        else -> null
    }
}
