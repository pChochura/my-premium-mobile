package com.pointlessapps.mypremiummobile.remote.datasource.payments.dto

internal data class ChangeDeliveryMethodBodyDto(
    val action: Boolean,
    val methodId: Int,
)
