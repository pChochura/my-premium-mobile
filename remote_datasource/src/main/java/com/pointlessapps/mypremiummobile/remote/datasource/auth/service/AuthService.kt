package com.pointlessapps.mypremiummobile.remote.datasource.auth.service

import com.pointlessapps.mypremiummobile.http.authorization.AUTHORIZATION_HEADER
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.LoginBodyDto
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.LoginResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.RefreshTokenResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

internal interface AuthService {

    @POST("auth/login")
    suspend fun login(
        @Body loginBodyDto: LoginBodyDto,
    ): LoginResponseDto

    @POST("auth/logout")
    suspend fun logout()

    @POST("auth/refreshToken")
    suspend fun refreshToken(
        @Header(AUTHORIZATION_HEADER) authHeader: String,
    ): Response<RefreshTokenResponseDto>
}
