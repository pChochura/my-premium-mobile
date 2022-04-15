package com.pointlessapps.mypremiummobile.remote.datasource.payments.dto

import androidx.annotation.Keep

@Keep
internal data class ChangeDeliveryMethodBodyDto(
    val action: Boolean,
    val methodId: Int,
)
