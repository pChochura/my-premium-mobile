package com.pointlessapps.mypremiummobile.datasource.services

import com.pointlessapps.mypremiummobile.datasource.services.dto.InternetPackageResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.InternetPackageStatusResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.PhoneNumberResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.UserOfferResponse

interface ServicesDatasource {
    suspend fun getUserPhoneNumbers(): List<PhoneNumberResponse>

    suspend fun getUserOffer(phoneNumberId: String): UserOfferResponse

    suspend fun getInternetPackageStatus(phoneNumberId: String): InternetPackageStatusResponse

    suspend fun getInternetPackages(phoneNumberId: String): List<InternetPackageResponse>
}
