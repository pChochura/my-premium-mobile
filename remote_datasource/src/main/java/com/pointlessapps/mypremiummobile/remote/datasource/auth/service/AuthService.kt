package com.pointlessapps.mypremiummobile.remote.datasource.auth.service

import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.LoginBodyDto
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.LoginResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthService {

    @POST("auth/login")
    suspend fun login(
        @Body loginBodyDto: LoginBodyDto,
    ): LoginResponseDto

    @POST("auth/logout")
    suspend fun logout()
}
