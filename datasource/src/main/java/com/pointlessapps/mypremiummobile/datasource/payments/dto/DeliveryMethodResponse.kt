package com.pointlessapps.mypremiummobile.datasource.payments.dto

data class DeliveryMethodResponse(
    val id: Int,
    val method: String,
    val price: String,
    val status: Boolean,
    val text: String,
)
