package com.pointlessapps.mypremiummobile.datasource.services.dto

data class UserOfferResponse(
    val tariff: String,
    val callsMobile: String,
    val callsLandLine: String,
    val sms: String,
    val mms: String,
    val internet: String,
)
