package com.pointlessapps.mypremiummobile.http.errors.model

import com.google.gson.annotations.SerializedName

internal data class Error(
    @SerializedName("error")
    val name: String,
    @SerializedName("error_description")
    val message: String? = null,
)
