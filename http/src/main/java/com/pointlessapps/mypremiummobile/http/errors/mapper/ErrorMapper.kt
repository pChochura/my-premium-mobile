package com.pointlessapps.mypremiummobile.http.errors.mapper

import com.pointlessapps.mypremiummobile.errors.AuthorizationInvalidUserCredentialsException
import com.pointlessapps.mypremiummobile.http.errors.model.Error
import java.net.HttpURLConnection

/**
 * Maps an [Error] instance to an appropriate [Exception] based on the status code of the response
 */
internal fun Error?.toException(httpErrorCode: Int) = when {
    this == null -> RuntimeException()
    httpErrorCode == HttpURLConnection.HTTP_BAD_REQUEST ->
        AuthorizationInvalidUserCredentialsException(message)
    else -> RuntimeException(message)
}
