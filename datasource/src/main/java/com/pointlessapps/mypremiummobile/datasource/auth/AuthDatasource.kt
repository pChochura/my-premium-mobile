package com.pointlessapps.mypremiummobile.datasource.auth

import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse

interface AuthDatasource {
    fun isLoggedIn(): Boolean

    suspend fun login(login: String, password: String): UserInfoResponse

    suspend fun logout()

    suspend fun sendVerificationCode(): Boolean

    suspend fun resendVerificationCode(): Boolean

    suspend fun validateCode(code: String): UserInfoResponse
}
