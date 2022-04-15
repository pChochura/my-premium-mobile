package com.pointlessapps.mypremiummobile.http.errors.model

internal data class Error(
    val name: String,
    val message: String? = null,
)
