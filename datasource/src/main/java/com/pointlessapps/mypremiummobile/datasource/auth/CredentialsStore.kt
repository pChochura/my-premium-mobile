package com.pointlessapps.mypremiummobile.datasource.auth

import com.pointlessapps.mypremiummobile.datasource.auth.dto.Credentials

interface CredentialsStore {
    fun setCredentials(login: String, password: String)

    fun getCredentials(): Credentials?

    fun clear()
}
