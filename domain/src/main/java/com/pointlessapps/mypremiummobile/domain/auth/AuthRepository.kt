package com.pointlessapps.mypremiummobile.domain.auth

import com.pointlessapps.mypremiummobile.datasource.auth.AuthDatasource
import com.pointlessapps.mypremiummobile.domain.auth.mapper.toLoginResponse
import com.pointlessapps.mypremiummobile.domain.auth.model.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface AuthRepository {
    fun login(login: String, password: String): Flow<LoginResponse>

    fun logout(): Flow<Unit>
}

internal class AuthRepositoryImpl(
    private val authDatasource: AuthDatasource,
) : AuthRepository {

    override fun login(login: String, password: String) = flow {
        emit(authDatasource.login(login, password).toLoginResponse())
    }.flowOn(Dispatchers.IO)

    override fun logout() = flow {
        emit(authDatasource.logout())
    }.flowOn(Dispatchers.IO)
}
