package com.pointlessapps.mypremiummobile.compose.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.pointlessapps.mypremiummobile.LocalSnackbarHostState
import com.pointlessapps.mypremiummobile.R
import com.pointlessapps.mypremiummobile.compose.model.InputError
import com.pointlessapps.mypremiummobile.compose.ui.components.*
import org.koin.androidx.compose.getViewModel

private const val LOGO_WIDTH_RATIO = 0.65f

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = getViewModel(),
    onShowDashboard: () -> Unit,
) {
    val snackbarHost = LocalSnackbarHostState.current
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginEvent.MoveToNextScreen ->
                    onShowDashboard()
                is LoginEvent.ShowErrorMessage ->
                    snackbarHost.showSnackbar(event.message)
            }
        }
    }

    ComposeLoader(enabled = viewModel.state.isLoading)

    ComposeScaffoldLayout(includeBottomNavigationBar = false) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .imePadding()
                .padding(padding)
                .padding(dimensionResource(id = R.dimen.big_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_padding)),
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(LOGO_WIDTH_RATIO)
                    .padding(top = dimensionResource(id = R.dimen.big_padding)),
                painter = painterResource(id = R.drawable.my_premium_mobile_logo),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.weight(1f))

            ComposeTextField(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.big_padding)),
                value = viewModel.state.login.value,
                onValueChange = viewModel::onLoginChanged,
                label = stringResource(id = R.string.login_or_phone_number),
                onFocusLost = viewModel::onLoginEditingFinished,
                onImeAction = { viewModel.onLoginEditingFinished() },
                error = InputError(
                    enabled = viewModel.state.login.error,
                    errorMessage = stringResource(id = R.string.incorrect_login_or_phone_number),
                ),
                textFieldStyle = defaultComposeTextFieldStyle().copy(
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    placeholder = stringResource(id = R.string.enter_your_login),
                ),
            )

            ComposeTextField(
                value = viewModel.state.password.value,
                onValueChange = viewModel::onPasswordChanged,
                label = stringResource(id = R.string.password),
                onFocusLost = viewModel::onPasswordEditingFinished,
                onImeAction = { viewModel.onPasswordEditingFinished(byImeAction = true) },
                error = InputError(
                    enabled = viewModel.state.password.error,
                    errorMessage = stringResource(id = R.string.incorrect_password),
                ),
                textFieldStyle = defaultComposeTextFieldStyle().copy(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done,
                    ),
                    placeholder = stringResource(id = R.string.enter_your_password),
                ),
            )

            Spacer(modifier = Modifier.weight(1f))

            ComposeButton(
                modifier = Modifier
                    .fillMaxWidth(LOGO_WIDTH_RATIO)
                    .padding(top = dimensionResource(id = R.dimen.big_padding)),
                text = stringResource(id = R.string.login),
                onClick = viewModel::onLoginClicked,
                buttonStyle = defaultComposeButtonStyle().copy(
                    enabled = viewModel.state.isButtonEnabled,
                ),
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.big_padding)))
        }
    }
}
