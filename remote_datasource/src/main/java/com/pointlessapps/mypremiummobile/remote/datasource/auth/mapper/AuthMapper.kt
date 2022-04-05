package com.pointlessapps.mypremiummobile.remote.datasource.auth.mapper

import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.LoginResponseDto as RemoteLoginResponseDto

internal fun RemoteLoginResponseDto?.toUserInfoResponse(): UserInfoResponse =
    requireNotNull(this).run {
        UserInfoResponse(
            email = requireNotNull(email),
            name = requireNotNull(name),
        )
    }
