package com.pointlessapps.mypremiummobile.remote.datasource.auth

import com.pointlessapps.mypremiummobile.datasource.auth.UserInfoDatasource
import com.pointlessapps.mypremiummobile.remote.datasource.auth.mapper.toUserInfoResponse
import com.pointlessapps.mypremiummobile.remote.datasource.auth.service.UserInfoService

internal class UserInfoDatasourceImpl(
    private val userInfoService: UserInfoService,
) : UserInfoDatasource {

    override suspend fun getUserName() = userInfoService.getUserName().toUserInfoResponse()
}
