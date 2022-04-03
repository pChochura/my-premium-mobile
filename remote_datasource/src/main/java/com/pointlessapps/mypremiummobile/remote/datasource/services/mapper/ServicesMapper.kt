package com.pointlessapps.mypremiummobile.remote.datasource.services.mapper

import com.pointlessapps.mypremiummobile.datasource.services.dto.PhoneNumberResponse
import com.pointlessapps.mypremiummobile.remote.datasource.services.dto.PhoneNumberResponseDto

internal fun List<PhoneNumberResponseDto>.toPhoneNumbers() = map {
    PhoneNumberResponse(
        id = it.id,
        phoneNumber = it.msisdn,
        isMain = it.isMainNumber,
    )
}
