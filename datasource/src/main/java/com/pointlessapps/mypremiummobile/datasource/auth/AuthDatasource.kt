package com.pointlessapps.mypremiummobile.datasource.auth

import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse

interface AuthDatasource {
    suspend fun login(login: String, password: String): UserInfoResponse

    suspend fun logout()

    fun isLoggedIn(): Boolean
}
