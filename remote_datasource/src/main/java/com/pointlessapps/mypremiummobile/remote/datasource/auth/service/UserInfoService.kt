package com.pointlessapps.mypremiummobile.remote.datasource.auth.service

import com.pointlessapps.mypremiummobile.http.authorization.Authorize
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.LoginResponseDto
import retrofit2.http.GET

internal interface UserInfoService {

    @Authorize
    @GET("auth/getCustomerName")
    suspend fun getUserName(): LoginResponseDto
}
