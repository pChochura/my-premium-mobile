package com.pointlessapps.mypremiummobile.datasource.auth

import com.pointlessapps.mypremiummobile.datasource.auth.dto.LoginResponseDto

interface AuthDatasource {
    suspend fun login(login: String, password: String): LoginResponseDto

    suspend fun logout()
}
