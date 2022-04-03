package com.pointlessapps.mypremiummobile.remote.datasource.auth

import com.pointlessapps.mypremiummobile.datasource.auth.AuthDatasource
import com.pointlessapps.mypremiummobile.datasource.auth.AuthorizationTokenStore
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.LoginBodyDto
import com.pointlessapps.mypremiummobile.remote.datasource.auth.mapper.toLoginResponse
import com.pointlessapps.mypremiummobile.remote.datasource.auth.service.AuthService

internal class AuthDatasourceImpl(
    private val authorizationTokenStore: AuthorizationTokenStore,
    private val authService: AuthService,
) : AuthDatasource {

    override suspend fun login(login: String, password: String) = authService.login(
        LoginBodyDto(
            username = login,
            password = password,
        ),
    ).also { authorizationTokenStore.setToken(it.token) }
        .toLoginResponse()

    override suspend fun logout() = authService.logout()
}
