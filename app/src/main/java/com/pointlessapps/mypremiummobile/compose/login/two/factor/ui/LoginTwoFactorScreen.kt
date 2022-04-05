package com.pointlessapps.mypremiummobile.compose.login.two.factor.ui

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
internal fun LoginTwoFactorScreen(
    viewModel: LoginTwoFactorViewModel = getViewModel(),
    onShowDashboard: () -> Unit,
) {
    val snackbarHost = LocalSnackbarHostState.current
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                LoginTwoFactorEvent.MoveToNextScreen ->
                    onShowDashboard()
                is LoginTwoFactorEvent.ShowErrorMessage ->
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
                value = viewModel.state.code.value,
                onValueChange = viewModel::onCodeChanged,
                label = stringResource(id = R.string.verification_code),
                onFocusLost = viewModel::onCodeEditingFinished,
                onImeAction = { viewModel.onCodeEditingFinished(byImeAction = true) },
                error = InputError(
                    enabled = viewModel.state.code.error,
                    errorMessage = stringResource(id = R.string.incorrect_code),
                ),
                textFieldStyle = defaultComposeTextFieldStyle().copy(
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    placeholder = stringResource(id = R.string.enter_your_code),
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
                    enabled = viewModel.state.isLoginButtonEnabled,
                ),
            )

            ComposeButton(
                modifier = Modifier.fillMaxWidth(LOGO_WIDTH_RATIO),
                text = stringResource(id = R.string.resend_code),
                onClick = viewModel::onResendCodeClicked,
                buttonStyle = outlinedComposeButtonModel().copy(
                    enabled = viewModel.state.isResendCodeButtonEnabled,
                ),
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.big_padding)))
        }
    }
}
