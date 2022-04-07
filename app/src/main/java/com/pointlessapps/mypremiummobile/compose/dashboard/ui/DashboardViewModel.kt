package com.pointlessapps.mypremiummobile.compose.dashboard.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pointlessapps.mypremiummobile.R
import com.pointlessapps.mypremiummobile.compose.dashboard.model.DashboardModel
import com.pointlessapps.mypremiummobile.compose.dashboard.model.InternetPackage
import com.pointlessapps.mypremiummobile.compose.dashboard.model.InternetPackageStatus
import com.pointlessapps.mypremiummobile.compose.dashboard.model.UserOffer
import com.pointlessapps.mypremiummobile.compose.model.Balance
import com.pointlessapps.mypremiummobile.compose.model.UserInfo
import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.InternetPackageResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.InternetPackageStatusResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.PhoneNumberResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.UserOfferResponse
import com.pointlessapps.mypremiummobile.domain.services.usecase.BuyInternetPackageUseCase
import com.pointlessapps.mypremiummobile.domain.usecase.GetDashboardModelUseCase
import com.pointlessapps.mypremiummobile.errors.AuthorizationTokenExpiredException
import com.pointlessapps.mypremiummobile.utils.errors.ErrorHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
    data class ShowMessage(@StringRes val message: Int) : DashboardEvent
    data class ShowConfirmationDialog(
        val name: String,
        val number: String,
        val onConfirm: () -> Unit,
    ) : DashboardEvent
}

internal class DashboardViewModel(
    private val errorHandler: ErrorHandler,
    getDashboardModelUseCase: GetDashboardModelUseCase,
    private val buyInternetPackageUseCase: BuyInternetPackageUseCase,
) : ViewModel() {

    private val eventChannel = Channel<DashboardEvent>(Channel.RENDEZVOUS)
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(DashboardState())

    init {
        getDashboardModelUseCase()
            .take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .onEach { model ->
                val (userInfo, userOffer, balance, internetPackageStatus, internetPackages) = buildModel(
                    userInfo = model.userInfo,
                    phoneNumber = model.phoneNumber,
                    userOffer = model.userOffer,
                    balance = model.balance,
                    internetPackageStatus = model.internetPackageStatus,
                    internetPackages = model.internetPackages,
                )

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
                    DashboardEvent.ShowMessage(
                        errorHandler.mapThrowableToErrorMessage(throwable),
                    ),
                )
            }
            .launchIn(viewModelScope)
    }

    private fun buildModel(
        userInfo: UserInfoResponse,
        phoneNumber: PhoneNumberResponse,
        userOffer: UserOfferResponse,
        balance: BalanceResponse,
        internetPackageStatus: InternetPackageStatusResponse,
        internetPackages: List<InternetPackageResponse>,
    ) = DashboardModel(
        userInfo = UserInfo(
            email = userInfo.email,
            name = userInfo.name,
            phoneNumber = phoneNumber.phoneNumber,
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

    fun buyInternetPackage(internetPackage: InternetPackage) {
        viewModelScope.launch {
            eventChannel.send(
                DashboardEvent.ShowConfirmationDialog(
                    name = internetPackage.name,
                    number = requireNotNull(state.userInfo.phoneNumber),
                    onConfirm = { executeBuyInternetPackage(internetPackage.id) },
                ),
            )
        }
    }

    private fun executeBuyInternetPackage(id: Int) {
        buyInternetPackageUseCase(requireNotNull(state.userInfo.phoneNumber), id)
            .take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .onEach {
                state = state.copy(isLoading = false)

                eventChannel.send(
                    DashboardEvent.ShowMessage(R.string.succesfully_bought_internet_package),
                )
            }
            .catch { throwable ->
                Timber.e(throwable)

                state = state.copy(isLoading = false)
                eventChannel.send(
                    DashboardEvent.ShowMessage(errorHandler.mapThrowableToErrorMessage(throwable)),
                )
            }
            .launchIn(viewModelScope)
    }
}
