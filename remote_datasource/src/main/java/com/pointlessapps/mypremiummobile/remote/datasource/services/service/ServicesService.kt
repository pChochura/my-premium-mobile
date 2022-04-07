package com.pointlessapps.mypremiummobile.remote.datasource.services.service

import com.pointlessapps.mypremiummobile.http.authorization.Authorize
import com.pointlessapps.mypremiummobile.remote.datasource.services.dto.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface ServicesService {

    @Authorize
    @GET("services/getMsisdnsList")
    suspend fun getUserPhoneNumbers(): List<PhoneNumberResponseDto>

    @Authorize
    @POST("services/getSubscription")
    suspend fun getUserOffer(@Body phoneNumberId: String): UserOfferResponseDto

    @Authorize
    @POST("services/getPackageStatus")
    suspend fun getInternetPackageStatus(@Body phoneNumberId: String): InternetPackageStatusResponseDto

    @Authorize
    @POST("services/getPackageList")
    suspend fun getInternetPackages(@Body phoneNumberId: String): List<InternetPackageResponseDto>

    @Authorize
    @POST("services/addPackage")
    suspend fun buyInternetPackage(@Body buyInternetPackageBodyDto: BuyInternetPackageBodyDto)
}
