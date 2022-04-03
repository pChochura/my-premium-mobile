package com.pointlessapps.mypremiummobile.datasource.auth

import com.pointlessapps.mypremiummobile.datasource.auth.dto.LoginResponse

interface AuthDatasource {
    suspend fun login(login: String, password: String): LoginResponse

    suspend fun logout()
}
