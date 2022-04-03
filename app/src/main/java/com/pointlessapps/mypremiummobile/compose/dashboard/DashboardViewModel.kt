package com.pointlessapps.mypremiummobile.compose.dashboard

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pointlessapps.mypremiummobile.compose.model.Balance
import com.pointlessapps.mypremiummobile.compose.model.UserInfo
import com.pointlessapps.mypremiummobile.domain.payments.usecase.GetBalanceUseCase
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetUserPhoneNumbersUseCase
import com.pointlessapps.mypremiummobile.utils.errors.ErrorHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber

internal data class DashboardState(
    val userInfo: UserInfo,
    val balance: Balance = Balance(
        balance = "",
        billingPeriod = "",
        accountNumber = "",
    ),
    val isLoading: Boolean = false,
)

internal sealed interface DashboardEvent {
    data class ShowErrorMessage(@StringRes val message: Int) : DashboardEvent
}

internal class DashboardViewModel(
    userInfo: UserInfo,
    errorHandler: ErrorHandler,
    getUserPhoneNumbersUseCase: GetUserPhoneNumbersUseCase,
    getBalanceUseCase: GetBalanceUseCase,
) : ViewModel() {

    private val eventChannel = Channel<DashboardEvent>(Channel.RENDEZVOUS)
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(
        DashboardState(userInfo = userInfo),
    )

    init {
        combine(
            getUserPhoneNumbersUseCase.prepare(),
            getBalanceUseCase.prepare()
        ) { phoneNumbers, balance ->
            phoneNumbers to balance
        }.take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .onEach { (phoneNumbers, balance) ->
                state = state.copy(
                    userInfo = state.userInfo.copy(
                        phoneNumber = phoneNumbers.find { it.isMain }?.phoneNumber
                    ),
                    balance = Balance(
                        balance = balance.balance,
                        billingPeriod = balance.billingPeriod,
                        accountNumber = balance.individualBankAccountNumber,
                    ),
                    isLoading = false,
                )
            }
            .catch { throwable ->
                Timber.d(throwable)
                state = state.copy(isLoading = false)
                eventChannel.send(
                    DashboardEvent.ShowErrorMessage(
                        errorHandler.mapThrowableToErrorMessage(throwable),
                    ),
                )
            }
            .launchIn(viewModelScope)
    }
}
