package com.pointlessapps.mypremiummobile.domain.auth

import com.pointlessapps.mypremiummobile.datasource.auth.AuthDatasource
import com.pointlessapps.mypremiummobile.datasource.auth.UserInfoDatasource
import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface AuthRepository {
    fun login(login: String, password: String): Flow<UserInfoResponse>

    fun logout(): Flow<Unit>

    fun isLoggedIn(): Boolean

    fun getUserName(): Flow<UserInfoResponse>
}

internal class AuthRepositoryImpl(
    private val authDatasource: AuthDatasource,
    private val userInfoDatasource: UserInfoDatasource,
) : AuthRepository {

    override fun login(login: String, password: String) = flow {
        emit(authDatasource.login(login, password))
    }.flowOn(Dispatchers.IO)

    override fun logout() = flow {
        emit(authDatasource.logout())
    }.flowOn(Dispatchers.IO)

    override fun isLoggedIn() = authDatasource.isLoggedIn()

    override fun getUserName() = flow {
        emit(userInfoDatasource.getUserName())
    }.flowOn(Dispatchers.IO)
}
