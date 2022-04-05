package com.pointlessapps.mypremiummobile.compose.login.two.factor.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pointlessapps.mypremiummobile.R
import com.pointlessapps.mypremiummobile.compose.model.InputModel
import com.pointlessapps.mypremiummobile.domain.auth.usecase.ResendVerificationCodeUseCase
import com.pointlessapps.mypremiummobile.domain.auth.usecase.SendVerificationCodeUseCase
import com.pointlessapps.mypremiummobile.domain.auth.usecase.ValidateVerificationCodeUseCase
import com.pointlessapps.mypremiummobile.domain.validation.usecase.ValidateSimpleInputUseCase
import com.pointlessapps.mypremiummobile.errors.AuthorizationInvalidUserCredentialsException
import com.pointlessapps.mypremiummobile.utils.errors.ErrorHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import timber.log.Timber

internal data class LoginTwoFactorState(
    val code: InputModel = InputModel(),
    val isLoginButtonEnabled: Boolean = false,
    val isResendCodeButtonEnabled: Boolean = true,
    val isLoading: Boolean = false,
)

internal sealed interface LoginTwoFactorEvent {
    object MoveToNextScreen : LoginTwoFactorEvent
    data class ShowErrorMessage(@StringRes val message: Int) : LoginTwoFactorEvent
}

internal class LoginTwoFactorViewModel(
    private val errorHandler: ErrorHandler,
    private val validateSimpleInputUseCase: ValidateSimpleInputUseCase,
    sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    private val resendVerificationCodeUseCase: ResendVerificationCodeUseCase,
    private val validateVerificationCodeUseCase: ValidateVerificationCodeUseCase,
) : ViewModel() {

    private val eventChannel = Channel<LoginTwoFactorEvent>(Channel.RENDEZVOUS)
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(LoginTwoFactorState())

    init {
        sendVerificationCodeUseCase()
            .take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .onEach {
                state = state.copy(isLoading = false)
                // TODO ask for reading sms
            }
            .catch { throwable ->
                Timber.e(throwable)
                state = state.copy(isLoading = false)

                eventChannel.send(
                    LoginTwoFactorEvent.ShowErrorMessage(
                        errorHandler.mapThrowableToErrorMessage(throwable),
                    ),
                )
            }
            .launchIn(viewModelScope)
    }

    fun onCodeChanged(value: String) {
        state = state.copy(
            code = InputModel(value = value),
            isLoginButtonEnabled = validateSimpleInputUseCase(value),
        )
    }

    fun onCodeEditingFinished(byImeAction: Boolean = false) {
        val code = state.code
        val isCodeValid = validateSimpleInputUseCase(code.value)
        state = state.copy(
            code = code.copy(
                error = !isCodeValid,
            ),
        )

        if (byImeAction && isCodeValid) {
            onLoginClicked()
        }
    }

    fun onLoginClicked() {
        validateVerificationCodeUseCase(state.code.value)
            .take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .onEach {
                state = state.copy(isLoading = false)
                eventChannel.send(LoginTwoFactorEvent.MoveToNextScreen)
            }
            .catch { throwable ->
                Timber.e(throwable)
                state = state.copy(isLoading = false)

                if (throwable is AuthorizationInvalidUserCredentialsException) {
                    eventChannel.send(
                        LoginTwoFactorEvent.ShowErrorMessage(
                            R.string.incorrect_verification_code,
                        ),
                    )
                } else {
                    eventChannel.send(
                        LoginTwoFactorEvent.ShowErrorMessage(
                            errorHandler.mapThrowableToErrorMessage(throwable),
                        ),
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun onResendCodeClicked() {
        resendVerificationCodeUseCase()
            .take(1)
            .onStart {
                state = state.copy(
                    isLoading = true,
                    isResendCodeButtonEnabled = false,
                )
            }
            .onEach {
                state = state.copy(isLoading = false)
                // TODO ask for reading sms
            }
            .mapLatest { delay(TIME_TO_DISABLE_RESEND_BUTTON) }
            .onEach {
                state = state.copy(isResendCodeButtonEnabled = true)
            }
            .catch { throwable ->
                Timber.e(throwable)
                state = state.copy(isLoading = false)

                eventChannel.send(
                    LoginTwoFactorEvent.ShowErrorMessage(
                        errorHandler.mapThrowableToErrorMessage(throwable),
                    ),
                )
            }
            .launchIn(viewModelScope)
    }

    companion object {
        private const val TIME_TO_DISABLE_RESEND_BUTTON = 5000L
    }
}
