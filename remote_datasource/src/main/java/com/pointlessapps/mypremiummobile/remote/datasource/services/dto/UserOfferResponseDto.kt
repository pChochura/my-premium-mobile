package com.pointlessapps.mypremiummobile.remote.datasource.services.dto

data class UserOfferResponseDto(
    val tariff: String,
    val callsMobile: String,
    val callsLandLine: String,
    val sms: String,
    val mms: String,
    val internet: String,
)
