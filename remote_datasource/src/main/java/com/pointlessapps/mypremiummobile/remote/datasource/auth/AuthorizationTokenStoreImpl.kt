package com.pointlessapps.mypremiummobile.remote.datasource.auth

import com.pointlessapps.mypremiummobile.datasource.auth.AuthorizationTokenStore
import com.pointlessapps.mypremiummobile.http.authorization.AUTHORIZATION_BEARER
import com.pointlessapps.mypremiummobile.remote.datasource.auth.service.AuthService
import kotlinx.coroutines.runBlocking
import org.koin.java.KoinJavaComponent.inject

internal class AuthorizationTokenStoreImpl : AuthorizationTokenStore {

    private val authService: AuthService by inject(AuthService::class.java)

    private var authToken: String? = null

    override fun setToken(authToken: String?) {
        this.authToken = authToken
    }

    override fun getAuthToken() = authToken

    override fun refreshToken() = runBlocking {
        val response = runCatching {
            authService.refreshToken("$AUTHORIZATION_BEARER $authToken")
        }.getOrNull()
        setToken(response?.body()?.token)

        return@runBlocking response?.isSuccessful ?: false
    }
}
