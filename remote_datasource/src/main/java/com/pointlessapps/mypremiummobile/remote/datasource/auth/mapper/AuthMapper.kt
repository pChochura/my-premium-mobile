package com.pointlessapps.mypremiummobile.remote.datasource.auth.mapper

import com.pointlessapps.mypremiummobile.datasource.auth.dto.LoginResponse
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.LoginResponseDto as RemoteLoginResponseDto

internal fun RemoteLoginResponseDto?.toLoginResponse(): LoginResponse =
    requireNotNull(this).run {
        LoginResponse(
            email = email,
            name = name,
            token = token,
        )
    }
