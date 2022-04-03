package com.pointlessapps.mypremiummobile.remote.datasource.services.service

import com.pointlessapps.mypremiummobile.http.authorization.Authorize
import com.pointlessapps.mypremiummobile.remote.datasource.services.dto.PhoneNumberResponseDto
import retrofit2.http.GET

internal interface ServicesService {

    @Authorize
    @GET("services/getMsisdnsList")
    suspend fun getUserPhoneNumbers(): List<PhoneNumberResponseDto>
}
