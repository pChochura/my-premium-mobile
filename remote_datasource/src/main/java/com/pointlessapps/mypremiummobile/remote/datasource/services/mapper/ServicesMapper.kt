package com.pointlessapps.mypremiummobile.remote.datasource.services.mapper

import com.pointlessapps.mypremiummobile.datasource.services.dto.InternetPackageResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.InternetPackageStatusResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.PhoneNumberResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.UserOfferResponse
import com.pointlessapps.mypremiummobile.remote.datasource.services.dto.InternetPackageResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.services.dto.InternetPackageStatusResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.services.dto.PhoneNumberResponseDto
import com.pointlessapps.mypremiummobile.remote.datasource.services.dto.UserOfferResponseDto
import java.text.SimpleDateFormat

internal fun List<PhoneNumberResponseDto>.toPhoneNumbers() = map {
    PhoneNumberResponse(
        id = it.id,
        phoneNumber = it.msisdn,
        isMain = it.isMainNumber,
    )
}

internal fun UserOfferResponseDto.toUserOffer() = UserOfferResponse(
    tariff = tariff,
    callsMobile = callsMobile,
    callsLandLine = callsLandLine,
    sms = sms,
    mms = mms,
    internet = internet,
)

internal fun InternetPackageStatusResponseDto.toInternetPackageStatus(
    dateParser: SimpleDateFormat,
) = InternetPackageStatusResponse(
    currentStatus = currentStatus,
    limit = limit,
    limitDate = requireNotNull(dateParser.parse(limitDate)),
)

internal fun List<InternetPackageResponseDto>.toInternetPackages() = map {
    InternetPackageResponse(
        id = it.id,
        name = it.name,
        amount = it.amount,
    )
}
