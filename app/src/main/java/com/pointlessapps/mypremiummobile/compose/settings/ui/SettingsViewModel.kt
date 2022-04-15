package com.pointlessapps.mypremiummobile.compose.settings.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pointlessapps.mypremiummobile.compose.model.UserInfo
import com.pointlessapps.mypremiummobile.compose.settings.model.NotificationMethod
import com.pointlessapps.mypremiummobile.compose.settings.model.Settings
import com.pointlessapps.mypremiummobile.compose.settings.model.SettingsModel
import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.PhoneNumberResponse
import com.pointlessapps.mypremiummobile.datasource.settings.dto.NotificationMethodsResponse
import com.pointlessapps.mypremiummobile.datasource.settings.dto.SettingsResponse
import com.pointlessapps.mypremiummobile.domain.auth.usecase.LogoutUseCase
import com.pointlessapps.mypremiummobile.domain.usecase.GetSettingsModelUseCase
import com.pointlessapps.mypremiummobile.utils.errors.ErrorHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber

internal data class SettingsState(
    val userInfo: UserInfo = UserInfo(),
    val settings: Settings = Settings(),
    val notificationMethods: List<NotificationMethod> = emptyList(),
    val isLoading: Boolean = false,
)

internal sealed interface SettingsEvent {
    object MoveToLoginScreen : SettingsEvent
    data class ShowErrorMessage(@StringRes val message: Int) : SettingsEvent
}

internal class SettingsViewModel(
    private val errorHandler: ErrorHandler,
    getSettingsModelUseCase: GetSettingsModelUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val eventChannel = Channel<SettingsEvent>(Channel.RENDEZVOUS)
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(SettingsState())

    init {
        getSettingsModelUseCase()
            .take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .onEach {
                val (userInfo, settings, notificationMethods) = buildModel(
                    userInfo = it.userInfo,
                    phoneNumber = it.phoneNumber,
                    settings = it.settings,
                    notificationMethods = it.notificationMethods,
                )
                state = state.copy(
                    userInfo = userInfo,
                    settings = settings,
                    notificationMethods = notificationMethods,
                    isLoading = false,
                )
            }
            .catch { throwable ->
                Timber.e(throwable)
                state = state.copy(isLoading = false)

                eventChannel.send(
                    SettingsEvent.ShowErrorMessage(
                        errorHandler.mapThrowableToErrorMessage(throwable),
                    ),
                )
            }
            .launchIn(viewModelScope)
    }

    private fun buildModel(
        userInfo: UserInfoResponse,
        phoneNumber: PhoneNumberResponse,
        settings: SettingsResponse,
        notificationMethods: NotificationMethodsResponse,
    ) = SettingsModel(
        userInfo = UserInfo(
            email = userInfo.email,
            name = userInfo.name,
            phoneNumber = phoneNumber.phoneNumber,
        ),
        settings = Settings(
            login = settings.login,
            notificationMethod = requireNotNull(
                when {
                    notificationMethods.email -> NotificationMethod.EMAIL
                    notificationMethods.sms -> NotificationMethod.SMS
                    else -> null
                },
            ),
            isTwoFactorAuthEnabled = settings.twoFactorAuth,
        ),
        notificationMethods = NotificationMethod.values().toList(),
    )

    fun onLogoutClicked() {
        logoutUseCase()
            .take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .onEach {
                state = state.copy(isLoading = false)
                eventChannel.send(SettingsEvent.MoveToLoginScreen)
            }
            .catch { throwable ->
                Timber.e(throwable)

                state = state.copy(isLoading = false)
                eventChannel.send(
                    SettingsEvent.ShowErrorMessage(
                        errorHandler.mapThrowableToErrorMessage(throwable),
                    ),
                )
            }
            .launchIn(viewModelScope)
    }
}
