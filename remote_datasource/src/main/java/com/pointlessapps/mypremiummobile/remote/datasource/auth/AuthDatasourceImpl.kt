package com.pointlessapps.mypremiummobile.remote.datasource.auth

import com.pointlessapps.mypremiummobile.datasource.auth.AuthDatasource
import com.pointlessapps.mypremiummobile.datasource.auth.AuthorizationTokenStore
import com.pointlessapps.mypremiummobile.datasource.auth.CredentialsStore
import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse
import com.pointlessapps.mypremiummobile.errors.AuthorizationTwoFactorException
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.LoginBodyDto
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.ValidateCodeBodyDto
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.VerificationCodeBodyDto
import com.pointlessapps.mypremiummobile.remote.datasource.auth.mapper.toUserInfoResponse
import com.pointlessapps.mypremiummobile.remote.datasource.auth.service.AuthService

internal class AuthDatasourceImpl(
    private val credentialsStore: CredentialsStore,
    private val authorizationTokenStore: AuthorizationTokenStore,
    private val authService: AuthService,
) : AuthDatasource {

    override fun saveCredentials(login: String, password: String) =
        credentialsStore.setCredentials(login, password)

    override fun getCredentials() = requireNotNull(credentialsStore.getCredentials())

    override fun isLoggedIn() = credentialsStore.getCredentials() != null

    @Throws(AuthorizationTwoFactorException::class)
    override suspend fun login(login: String, password: String): UserInfoResponse {
        println("LOG!, logging In: $login, $password")
        val loginResponse = authService.login(
            LoginBodyDto(
                username = login,
                password = password,
            ),
        )

        authorizationTokenStore.setToken(loginResponse.token)

        if (loginResponse.email == null || loginResponse.name == null) {
            throw AuthorizationTwoFactorException()
        }

        return loginResponse.toUserInfoResponse()
    }

    override suspend fun logout() = authService.logout()
        .also { authorizationTokenStore.setToken(null) }

    override suspend fun sendVerificationCode() = authService.sendVerificationCode(
        verificationCodeBodyDto = VerificationCodeBodyDto(
            twoFactorToken = requireNotNull(authorizationTokenStore.getAuthToken()),
        ),
    ).success

    override suspend fun resendVerificationCode() = authService.resendVerificationCode(
        verificationCodeBodyDto = VerificationCodeBodyDto(
            twoFactorToken = requireNotNull(authorizationTokenStore.getAuthToken()),
        ),
    ).success

    override suspend fun validateCode(code: String) = authService.validateCode(
        validateCodeBodyDto = ValidateCodeBodyDto(
            oneTimeCode = code,
            twoFactorToken = requireNotNull(authorizationTokenStore.getAuthToken()),
        ),
    ).also { authorizationTokenStore.setToken(it.token) }
        .toUserInfoResponse()
}
