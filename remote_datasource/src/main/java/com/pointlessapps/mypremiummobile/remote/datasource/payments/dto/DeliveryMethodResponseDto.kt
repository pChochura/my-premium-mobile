package com.pointlessapps.mypremiummobile.remote.datasource.payments.dto

import androidx.annotation.Keep

@Keep
data class DeliveryMethodResponseDto(
    val id: Int,
    val method: String,
    val price: String,
    val status: Boolean,
    val text: String,
)
