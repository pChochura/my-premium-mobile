package com.pointlessapps.mypremiummobile.domain.usecase

import com.pointlessapps.mypremiummobile.domain.auth.usecase.GetUserNameUseCase
import com.pointlessapps.mypremiummobile.domain.model.DashboardModel
import com.pointlessapps.mypremiummobile.domain.payments.usecase.GetBalanceUseCase
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetInternetPackageStatusUseCase
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetInternetPackagesUseCase
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetUserOfferUseCase
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetUserPhoneNumbersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class GetDashboardModelUseCase(
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getUserPhoneNumbersUseCase: GetUserPhoneNumbersUseCase,
    private val getUserOfferUseCase: GetUserOfferUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getInternetPackageStatusUseCase: GetInternetPackageStatusUseCase,
    private val getInternetPackagesUseCase: GetInternetPackagesUseCase,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke() = getUserPhoneNumbersUseCase()
        .flatMapLatest { phoneNumbers ->
            val phoneNumber = requireNotNull(phoneNumbers.find { it.isMain })

            return@flatMapLatest combine(
                getUserNameUseCase(),
                getUserOfferUseCase(phoneNumber.id),
                getBalanceUseCase(),
                getInternetPackageStatusUseCase(phoneNumber.id),
                getInternetPackagesUseCase(phoneNumber.id),
            ) { userInfo, userOffer, balance, internetPackageStatus, internetPackages ->
                DashboardModel(
                    userInfo = userInfo,
                    phoneNumber = phoneNumber,
                    userOffer = userOffer,
                    balance = balance,
                    internetPackageStatus = internetPackageStatus,
                    internetPackages = internetPackages,
                )
            }
        }
}
