package com.pointlessapps.mypremiummobile.compose.login.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pointlessapps.mypremiummobile.compose.model.InputModel
import com.pointlessapps.mypremiummobile.domain.auth.usecase.LoginUseCase
import com.pointlessapps.mypremiummobile.domain.validation.usecase.ValidateSimpleInputUseCase
import com.pointlessapps.mypremiummobile.utils.errors.ErrorHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber

internal data class LoginState(
    //TODO:
    val login: InputModel = InputModel(value = "732824592"),
    val password: InputModel = InputModel(value = "Pipistrelus.3524"),
    val isButtonEnabled: Boolean = false,
    val isLoading: Boolean = false,
)

internal sealed interface LoginEvent {
    object MoveToNextScreen : LoginEvent
    data class ShowErrorMessage(@StringRes val message: Int) : LoginEvent
}

internal class LoginViewModel(
    private val errorHandler: ErrorHandler,
    private val loginUseCase: LoginUseCase,
    private val validateSimpleInputUseCase: ValidateSimpleInputUseCase,
) : ViewModel() {

    private val eventChannel = Channel<LoginEvent>(Channel.RENDEZVOUS)
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(LoginState())

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
                error = !validateSimpleInputUseCase.prepare(login.value),
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
                error = !validateSimpleInputUseCase.prepare(password.value),
            ),
        )

        if (byImeAction && isDataValid()) {
            onLoginClicked()
        }
    }

    private fun isDataValid(
        login: String = state.login.value,
        password: String = state.password.value,
    ): Boolean = validateSimpleInputUseCase.prepare(login) &&
            validateSimpleInputUseCase.prepare(password)

    fun onLoginClicked() {
        loginUseCase
            .prepare(state.login.value, state.password.value)
            .take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .onEach {
                state = state.copy(isLoading = false)
                eventChannel.send(LoginEvent.MoveToNextScreen)
            }
            .catch { throwable ->
                Timber.e(throwable)
                state = state.copy(isLoading = false)
                eventChannel.send(
                    LoginEvent.ShowErrorMessage(errorHandler.mapThrowableToErrorMessage(throwable)),
                )
            }
            .launchIn(viewModelScope)
    }
}
