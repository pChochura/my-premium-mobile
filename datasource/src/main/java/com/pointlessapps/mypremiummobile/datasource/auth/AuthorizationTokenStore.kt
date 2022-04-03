package com.pointlessapps.mypremiummobile.datasource.auth

interface AuthorizationTokenStore {

    fun setToken(authToken: String?)

    fun getAuthToken(): String?

    fun refreshToken(): Boolean
}
