package com.pointlessapps.mypremiummobile.compose.dashboard.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pointlessapps.mypremiummobile.compose.dashboard.model.*
import com.pointlessapps.mypremiummobile.compose.model.Balance
import com.pointlessapps.mypremiummobile.compose.model.UserInfo
import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.InternetPackageResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.InternetPackageStatusResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.UserOfferResponse
import com.pointlessapps.mypremiummobile.domain.auth.usecase.GetUserNameUseCase
import com.pointlessapps.mypremiummobile.domain.payments.usecase.GetBalanceUseCase
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetInternetPackageStatusUseCase
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetInternetPackagesUseCase
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetUserOfferUseCase
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetUserPhoneNumbersUseCase
import com.pointlessapps.mypremiummobile.errors.AuthorizationTokenExpiredException
import com.pointlessapps.mypremiummobile.utils.errors.ErrorHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber

internal data class DashboardState(
    val userInfo: UserInfo = UserInfo(),
    val userOffer: UserOffer = UserOffer(),
    val balance: Balance = Balance(),
    val internetPackageStatus: InternetPackageStatus = InternetPackageStatus(),
    val internetPackages: List<InternetPackage> = emptyList(),
    val isLoading: Boolean = false,
)

internal sealed interface DashboardEvent {
    object MoveToLoginScreen : DashboardEvent
    data class ShowErrorMessage(@StringRes val message: Int) : DashboardEvent
}

@OptIn(ExperimentalCoroutinesApi::class)
internal class DashboardViewModel(
    errorHandler: ErrorHandler,
    getUserNameUseCase: GetUserNameUseCase,
    getUserPhoneNumbersUseCase: GetUserPhoneNumbersUseCase,
    getUserOfferUseCase: GetUserOfferUseCase,
    getBalanceUseCase: GetBalanceUseCase,
    getInternetPackageStatusUseCase: GetInternetPackageStatusUseCase,
    getInternetPackagesUseCase: GetInternetPackagesUseCase,
) : ViewModel() {

    private val eventChannel = Channel<DashboardEvent>(Channel.RENDEZVOUS)
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(DashboardState())

    init {
        getUserPhoneNumbersUseCase.prepare()
            .take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .flatMapLatest { phoneNumbers ->
                val phoneNumber = requireNotNull(phoneNumbers.find { it.isMain }).run {
                    PhoneNumber(id = id, number = phoneNumber)
                }

                combine(
                    getUserNameUseCase.prepare(),
                    getUserOfferUseCase.prepare(phoneNumber.id),
                    getBalanceUseCase.prepare(),
                    getInternetPackageStatusUseCase.prepare(phoneNumber.id),
                    getInternetPackagesUseCase.prepare(phoneNumber.id),
                ) { userInfo, userOffer, balance, internetPackageStatus, internetPackages ->
                    buildModel(
                        userInfo = userInfo,
                        phoneNumber = phoneNumber,
                        userOffer = userOffer,
                        balance = balance,
                        internetPackageStatus = internetPackageStatus,
                        internetPackages = internetPackages,
                    )
                }
            }
            .onEach { (userInfo, userOffer, balance, internetPackageStatus, internetPackages) ->
                state = state.copy(
                    userInfo = userInfo,
                    userOffer = userOffer,
                    balance = balance,
                    internetPackageStatus = internetPackageStatus,
                    internetPackages = internetPackages,
                    isLoading = false,
                )
            }
            .catch { throwable ->
                Timber.e(throwable)

                if (throwable is AuthorizationTokenExpiredException) {
                    eventChannel.send(DashboardEvent.MoveToLoginScreen)
                }

                state = state.copy(isLoading = false)
                eventChannel.send(
                    DashboardEvent.ShowErrorMessage(
                        errorHandler.mapThrowableToErrorMessage(throwable),
                    ),
                )
            }
            .launchIn(viewModelScope)
    }

    private fun buildModel(
        userInfo: UserInfoResponse,
        phoneNumber: PhoneNumber,
        userOffer: UserOfferResponse,
        balance: BalanceResponse,
        internetPackageStatus: InternetPackageStatusResponse,
        internetPackages: List<InternetPackageResponse>,
    ) = DashboardModel(
        userInfo = UserInfo(
            email = userInfo.email,
            name = userInfo.name,
            phoneNumber = phoneNumber.number,
        ),
        userOffer = UserOffer(
            tariff = userOffer.tariff,
            callsMobile = userOffer.callsMobile,
            callsLandLine = userOffer.callsLandLine,
            sms = userOffer.sms,
            mms = userOffer.mms,
        ),
        balance = Balance(
            balance = balance.balance,
            billingPeriod = balance.billingPeriod,
            accountNumber = balance.individualBankAccountNumber,
        ),
        internetPackageStatus = InternetPackageStatus(
            currentStatus = internetPackageStatus.currentStatus,
            limit = internetPackageStatus.limit,
            limitDate = internetPackageStatus.limitDate,
        ),
        internetPackages = internetPackages.map {
            InternetPackage(
                id = it.id,
                name = it.name,
                amount = it.amount,
            )
        },
    )
}
