package com.pointlessapps.mypremiummobile.remote.datasource.auth.service

import com.pointlessapps.mypremiummobile.http.authorization.AUTHORIZATION_HEADER
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.*
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

    @POST("auth/getVerificationCodeMethod")
    suspend fun sendVerificationCode(
        @Body verificationCodeBodyDto: VerificationCodeBodyDto,
    ): VerificationCodeResponseDto

    @POST("auth/sendOneTime")
    suspend fun resendVerificationCode(
        @Body verificationCodeBodyDto: VerificationCodeBodyDto,
    ): VerificationCodeResponseDto

    @POST("auth/validateCode")
    suspend fun validateCode(
        @Body validateCodeBodyDto: ValidateCodeBodyDto,
    ): LoginResponseDto
}
