package com.pointlessapps.mypremiummobile.remote.datasource.auth.mapper

import com.pointlessapps.mypremiummobile.datasource.auth.dto.LoginResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.auth.dto.LoginResponseDto as RemoteLoginResponseDto

internal fun RemoteLoginResponseDto.toLoginResponseDto(): LoginResponseDto = LoginResponseDto(
    email = email,
    name = name,
    token = token,
)
