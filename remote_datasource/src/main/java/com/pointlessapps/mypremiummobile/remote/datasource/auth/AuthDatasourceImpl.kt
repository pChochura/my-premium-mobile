package com.pointlessapps.mypremiummobile.remote.datasource.auth

import com.pointlessapps.mypremiummobile.datasource.auth.AuthDatasource
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.LoginBodyDto
import com.pointlessapps.mypremiummobile.remote.datasource.auth.mapper.toLoginResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.auth.service.AuthService

internal class AuthDatasourceImpl(
    private val authService: AuthService,
) : AuthDatasource {

    override suspend fun login(login: String, password: String) = authService.login(
        LoginBodyDto(
            username = login,
            password = password,
        ),
    ).toLoginResponseDto()

    override suspend fun logout() = authService.logout()
}
