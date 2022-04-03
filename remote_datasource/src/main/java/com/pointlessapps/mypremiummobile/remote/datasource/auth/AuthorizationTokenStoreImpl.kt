package com.pointlessapps.mypremiummobile.remote.datasource.auth

import android.content.SharedPreferences
import com.pointlessapps.mypremiummobile.datasource.auth.AuthorizationTokenStore
import com.pointlessapps.mypremiummobile.http.authorization.AUTHORIZATION_BEARER
import com.pointlessapps.mypremiummobile.remote.datasource.auth.service.AuthService
import kotlinx.coroutines.runBlocking
import org.koin.java.KoinJavaComponent.inject

internal class AuthorizationTokenStoreImpl(
    private val sharedPreferences: SharedPreferences,
) : AuthorizationTokenStore {

    private val authService: AuthService by inject(AuthService::class.java)

    private var authToken: String? = sharedPreferences.getString(AUTH_TOKEN_KEY, null)

    override fun setToken(authToken: String?) {
        this.authToken = authToken
        sharedPreferences.edit().apply {
            putString(AUTH_TOKEN_KEY, authToken)
        }.apply()
    }

    override fun getAuthToken() = authToken

    override fun refreshToken() = runBlocking {
        val response = runCatching {
            authService.refreshToken("$AUTHORIZATION_BEARER $authToken")
        }.getOrNull()
        setToken(response?.body()?.token)

        return@runBlocking response?.isSuccessful ?: false
    }

    companion object {
        const val SHARED_PREFERENCES_KEY = "token_store"

        private const val AUTH_TOKEN_KEY = "auth_token"
    }
}
