package com.pointlessapps.mypremiummobile.datasource.auth

import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse

interface UserInfoDatasource {
    suspend fun getUserName(): UserInfoResponse
}
