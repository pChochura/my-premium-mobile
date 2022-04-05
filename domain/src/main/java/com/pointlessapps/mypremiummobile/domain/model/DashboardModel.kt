package com.pointlessapps.mypremiummobile.domain.model

import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.InternetPackageResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.InternetPackageStatusResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.PhoneNumberResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.UserOfferResponse

data class DashboardModel(
    val userInfo: UserInfoResponse,
    val phoneNumber: PhoneNumberResponse,
    val userOffer: UserOfferResponse,
    val balance: BalanceResponse,
    val internetPackageStatus: InternetPackageStatusResponse,
    val internetPackages: List<InternetPackageResponse>,
)
