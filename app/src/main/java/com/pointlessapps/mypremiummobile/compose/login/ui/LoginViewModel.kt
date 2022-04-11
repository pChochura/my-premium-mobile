package com.pointlessapps.mypremiummobile.compose.login.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pointlessapps.mypremiummobile.compose.model.InputModel
import com.pointlessapps.mypremiummobile.domain.auth.usecase.GetCredentialsUseCase
import com.pointlessapps.mypremiummobile.domain.auth.usecase.IsLoggedInUseCase
import com.pointlessapps.mypremiummobile.domain.auth.usecase.LoginUseCase
import com.pointlessapps.mypremiummobile.domain.auth.usecase.SaveCredentialsUseCase
import com.pointlessapps.mypremiummobile.domain.validation.usecase.ValidateSimpleInputUseCase
import com.pointlessapps.mypremiummobile.errors.AuthorizationTwoFactorException
import com.pointlessapps.mypremiummobile.utils.errors.ErrorHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber

internal data class LoginState(
    val login: InputModel = InputModel(),
    val password: InputModel = InputModel(),
    val isButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

internal sealed interface LoginEvent {
    object MoveToNextScreen : LoginEvent
    object MoveToTwoFactorAuth : LoginEvent
    data class ShowErrorMessage(@StringRes val message: Int) : LoginEvent
}

@OptIn(ExperimentalCoroutinesApi::class)
internal class LoginViewModel(
    private val errorHandler: ErrorHandler,
    private val loginUseCase: LoginUseCase,
    isLoggedInUseCase: IsLoggedInUseCase,
    getCredentialsUseCase: GetCredentialsUseCase,
    private val saveCredentialsUseCase: SaveCredentialsUseCase,
    private val validateSimpleInputUseCase: ValidateSimpleInputUseCase,
) : ViewModel() {

    private val eventChannel = Channel<LoginEvent>(Channel.RENDEZVOUS)
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(LoginState())

    init {
        if (isLoggedInUseCase()) {
            getCredentialsUseCase()
                .onStart {
                    state = state.copy(isLoading = true)
                }
                .flatMapLatest { credentials ->
                    state = state.copy(
                        login = InputModel(
                            value = credentials.username,
                        ),
                        password = InputModel(
                            value = credentials.password,
                        ),
                        isButtonEnabled = isDataValid(
                            login = credentials.username,
                            password = credentials.password,
                        ),
                    )
                    loginUseCase(credentials.username, credentials.password)
                }
                .onEach {
                    state = state.copy(isLoading = false)
                    eventChannel.send(LoginEvent.MoveToNextScreen)
                }
                .catch { throwable ->
                    Timber.e(throwable)
                    state = state.copy(isLoading = false)

                    if (throwable is AuthorizationTwoFactorException) {
                        eventChannel.send(LoginEvent.MoveToTwoFactorAuth)
                    } else {
                        eventChannel.send(
                            LoginEvent.ShowErrorMessage(
                                errorHandler.mapThrowableToErrorMessage(throwable),
                            ),
                        )
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun onLoginChanged(value: String) {
        state = state.copy(
            login = InputModel(value = value),
            isButtonEnabled = isDataValid(login = value),
        )
    }

    fun onLoginEditingFinished() {
        val login = state.login
        state = state.copy(
            login = login.copy(
                error = !validateSimpleInputUseCase(login.value),
            ),
        )
    }

    fun onPasswordChanged(value: String) {
        state = state.copy(
            password = InputModel(value = value),
            isButtonEnabled = isDataValid(password = value),
        )
    }

    fun onPasswordEditingFinished(byImeAction: Boolean = false) {
        val password = state.password
        state = state.copy(
            password = password.copy(
                error = !validateSimpleInputUseCase(password.value),
            ),
        )

        if (byImeAction && isDataValid()) {
            onLoginClicked()
        }
    }

    private fun isDataValid(
        login: String = state.login.value,
        password: String = state.password.value,
    ): Boolean = validateSimpleInputUseCase(login) && validateSimpleInputUseCase(password)

    fun onLoginClicked() {
        loginUseCase(state.login.value, state.password.value)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .flatMapLatest {
                saveCredentialsUseCase(
                    login = state.login.value,
                    password = state.password.value,
                )
            }
            .onEach {
                state = state.copy(isLoading = false)
                eventChannel.send(LoginEvent.MoveToNextScreen)
            }
            .catch { throwable ->
                Timber.e(throwable)
                state = state.copy(isLoading = false)

                if (throwable is AuthorizationTwoFactorException) {
                    eventChannel.send(LoginEvent.MoveToTwoFactorAuth)
                } else {
                    eventChannel.send(
                        LoginEvent.ShowErrorMessage(
                            errorHandler.mapThrowableToErrorMessage(throwable),
                        ),
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
