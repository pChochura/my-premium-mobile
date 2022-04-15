package com.pointlessapps.mypremiummobile.compose.settings.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.pointlessapps.mypremiummobile.LocalSnackbarHostState
import com.pointlessapps.mypremiummobile.R
import com.pointlessapps.mypremiummobile.compose.ui.components.*
import org.koin.androidx.compose.getViewModel

private const val BUTTON_WIDTH_RATIO = 0.65f

@Composable
internal fun SettingsScreen(
    viewModel: SettingsViewModel = getViewModel(),
    onShowLogin: () -> Unit,
) {
    val snackbarHost = LocalSnackbarHostState.current
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SettingsEvent.MoveToLoginScreen ->
                    onShowLogin()
                is SettingsEvent.ShowErrorMessage ->
                    snackbarHost.showSnackbar(event.message)
            }
        }
    }

    ComposeLoader(enabled = viewModel.state.isLoading)

    ComposeScaffoldLayout(
        topBar = { TopBar(viewModel.state.userInfo) },
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Surface(
                modifier = Modifier
                    .padding(padding)
                    .navigationBarsPadding()
                    .imePadding()
                    .background(MaterialTheme.colors.background)
                    .verticalScroll(rememberScrollState())
                    .padding(dimensionResource(id = R.dimen.medium_padding)),
                color = MaterialTheme.colors.surface,
                shape = MaterialTheme.shapes.medium,
                elevation = dimensionResource(id = R.dimen.default_elevation),
            ) {
                Column {
                    SettingsRow(
                        title = R.string.change_login,
                        subtitle = viewModel.state.settings.login,
                        onClicked = {
                        },
                    )
                    Divider()
                    SettingsRow(
                        title = R.string.change_password,
                        onClicked = {
                        },
                    )
                    Divider()
                    SettingsRow(
                        title = R.string.notification_method,
                        subtitle = viewModel.state.settings.notificationMethod.name,
                        onClicked = {
                        },
                    )
                    Divider()
                    SettingsRow(
                        title = R.string.two_factor_authentication,
                        checkable = true,
                        checked = viewModel.state.settings.isTwoFactorAuthEnabled,
                        onClicked = {
                        },
                    )
                }
            }

            ComposeButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(BUTTON_WIDTH_RATIO)
                    .padding(bottom = padding.calculateBottomPadding())
                    .navigationBarsPadding()
                    .imePadding(),
                text = stringResource(id = R.string.logout),
                onClick = viewModel::onLogoutClicked,
            )
        }
    }
}

@Composable
private fun SettingsRow(
    @StringRes title: Int,
    subtitle: String? = null,
    checkable: Boolean = false,
    checked: Boolean = false,
    onClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClicked)
            .padding(dimensionResource(id = R.dimen.medium_padding)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))) {
            ComposeText(
                text = stringResource(id = title),
                textStyle = defaultComposeTextStyle().copy(
                    textColor = MaterialTheme.colors.onSurface,
                    typography = MaterialTheme.typography.body1,
                ),
            )
            subtitle?.let {
                Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.tiny_padding))) {
                    ComposeText(
                        text = stringResource(id = R.string.current),
                        textStyle = defaultComposeTextStyle().copy(
                            textColor = MaterialTheme.colors.onSurface,
                            typography = MaterialTheme.typography.caption,
                        ),
                    )
                    ComposeText(
                        text = subtitle,
                        textStyle = defaultComposeTextStyle().copy(
                            textColor = colorResource(id = R.color.accent),
                            typography = MaterialTheme.typography.caption,
                        ),
                    )
                }
            }
        }

        if (checkable) {
            ComposeCheckbox(
                checked = checked,
                onChecked = { onClicked() },
                checkboxStyle = defaultComposeCheckboxStyle().copy(enabled = false),
            )
        }
    }
}

@Composable
private fun Divider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.medium_padding))
            .height(dimensionResource(id = R.dimen.divider_height))
            .background(colorResource(id = R.color.accent_variant)),
    )
}
