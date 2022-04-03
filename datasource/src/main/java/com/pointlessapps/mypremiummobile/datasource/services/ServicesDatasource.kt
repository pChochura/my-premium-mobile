package com.pointlessapps.mypremiummobile.datasource.services

import com.pointlessapps.mypremiummobile.datasource.services.dto.PhoneNumberResponse

interface ServicesDatasource {
    suspend fun getUserPhoneNumbers(): List<PhoneNumberResponse>
}
