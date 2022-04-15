package com.pointlessapps.mypremiummobile.remote.datasource.services.dto

import androidx.annotation.Keep

@Keep
internal data class BuyInternetPackageBodyDto(
    val msisdn: String,
    val packageId: Int,
)
