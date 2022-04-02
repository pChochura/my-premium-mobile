package com.pointlessapps.mypremiummobile.domain.auth.mapper

import com.pointlessapps.mypremiummobile.datasource.auth.dto.LoginResponseDto
import com.pointlessapps.mypremiummobile.domain.auth.model.LoginResponse

internal fun LoginResponseDto.toLoginResponse() = LoginResponse(
    email = email,
    name = name,
    token = token,
)
