package com.pointlessapps.mypremiummobile.domain.auth

import com.pointlessapps.mypremiummobile.datasource.auth.AuthDatasource
import com.pointlessapps.mypremiummobile.datasource.auth.UserInfoDatasource
import com.pointlessapps.mypremiummobile.datasource.auth.dto.Credentials
import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface AuthRepository {
    fun saveCredentials(login: String, password: String): Flow<Unit>

    fun getCredentials(): Flow<Credentials>

    fun isLoggedIn(): Boolean

    fun login(login: String, password: String): Flow<UserInfoResponse>

    fun logout(): Flow<Unit>

    fun sendVerificationCode(): Flow<Boolean>

    fun resendVerificationCode(): Flow<Boolean>

    fun validateCode(code: String): Flow<UserInfoResponse>

    fun getUserName(): Flow<UserInfoResponse>
}

internal class AuthRepositoryImpl(
    private val authDatasource: AuthDatasource,
    private val userInfoDatasource: UserInfoDatasource,
) : AuthRepository {

    override fun saveCredentials(login: String, password: String) = flow {
        emit(authDatasource.saveCredentials(login, password))
    }.flowOn(Dispatchers.Default)

    override fun getCredentials() = flow {
        emit(authDatasource.getCredentials())
    }.flowOn(Dispatchers.Default)

    override fun isLoggedIn() = authDatasource.isLoggedIn()

    override fun login(login: String, password: String) = flow {
        emit(authDatasource.login(login, password))
    }.flowOn(Dispatchers.IO)

    override fun logout() = flow {
        emit(authDatasource.logout())
    }.flowOn(Dispatchers.IO)

    override fun sendVerificationCode() = flow {
        emit(authDatasource.sendVerificationCode())
    }.flowOn(Dispatchers.IO)

    override fun resendVerificationCode() = flow {
        emit(authDatasource.resendVerificationCode())
    }.flowOn(Dispatchers.IO)

    override fun validateCode(code: String) = flow {
        emit(authDatasource.validateCode(code))
    }.flowOn(Dispatchers.IO)

    override fun getUserName() = flow {
        emit(userInfoDatasource.getUserName())
    }.flowOn(Dispatchers.IO)
}
