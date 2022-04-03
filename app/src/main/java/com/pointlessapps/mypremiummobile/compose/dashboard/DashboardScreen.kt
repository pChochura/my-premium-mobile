package com.pointlessapps.mypremiummobile.compose.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.pointlessapps.mypremiummobile.LocalSnackbarHostState
import com.pointlessapps.mypremiummobile.R
import com.pointlessapps.mypremiummobile.compose.model.Balance
import com.pointlessapps.mypremiummobile.compose.model.UserInfo
import com.pointlessapps.mypremiummobile.compose.ui.components.ComposeLoader
import com.pointlessapps.mypremiummobile.compose.ui.components.ComposeScaffoldLayout
import com.pointlessapps.mypremiummobile.compose.ui.components.ComposeText
import com.pointlessapps.mypremiummobile.compose.ui.components.defaultComposeTextStyle
import org.koin.androidx.compose.getViewModel

@Composable
internal fun DashboardScreen(viewModel: DashboardViewModel = getViewModel()) {
    val snackbarHost = LocalSnackbarHostState.current
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is DashboardEvent.ShowErrorMessage ->
                    snackbarHost.showSnackbar(event.message)
            }
        }
    }

    ComposeLoader(enabled = viewModel.state.isLoading)

    ComposeScaffoldLayout(
        topBar = { TopBar(viewModel.state.userInfo) },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(dimensionResource(id = R.dimen.medium_padding))
                .navigationBarsPadding()
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.big_padding)),
        ) {
            AccountBalanceCard(viewModel.state.balance)
            YourOfferCard()
            AdditionalInternetPackageList()
        }
    }
}

@Composable
private fun AccountBalanceCard(balance: Balance) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.primary,
        shape = MaterialTheme.shapes.medium,
        elevation = dimensionResource(id = R.dimen.default_elevation),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.medium_padding)),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(dimensionResource(id = R.dimen.icon_button_size))
                    .clickable { },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = stringResource(id = R.string.show_payments_details),
                    tint = colorResource(id = R.color.accent_variant),
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))) {
                ComposeText(
                    text = stringResource(id = R.string.account_balance),
                    textStyle = defaultComposeTextStyle().copy(
                        textColor = colorResource(id = R.color.accent_variant),
                    ),
                )
                ComposeText(
                    text = stringResource(id = R.string.account_balance_value, balance.balance),
                    textStyle = defaultComposeTextStyle().copy(
                        textColor = colorResource(id = R.color.accent_variant),
                        typography = MaterialTheme.typography.h1,
                    ),
                )

                ComposeText(
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.small_padding),
                    ),
                    text = stringResource(id = R.string.billing_period),
                    textStyle = defaultComposeTextStyle().copy(
                        textColor = colorResource(id = R.color.accent_variant),
                    ),
                )
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .background(color = colorResource(id = R.color.accent_variant))
                        .padding(
                            vertical = dimensionResource(id = R.dimen.tiny_padding),
                            horizontal = dimensionResource(id = R.dimen.small_padding),
                        ),
                ) {
                    ComposeText(
                        text = stringResource(
                            id = R.string.billing_period_value,
                            balance.billingPeriod,
                        ),
                        textStyle = defaultComposeTextStyle().copy(
                            textColor = MaterialTheme.colors.onBackground,
                            typography = MaterialTheme.typography.caption,
                        ),
                    )
                }

                ComposeText(
                    text = stringResource(id = R.string.account_number),
                    textStyle = defaultComposeTextStyle().copy(
                        textColor = colorResource(id = R.color.accent_variant),
                    ),
                )
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .background(color = colorResource(id = R.color.accent_variant))
                        .padding(
                            vertical = dimensionResource(id = R.dimen.tiny_padding),
                            horizontal = dimensionResource(id = R.dimen.small_padding),
                        ),
                ) {
                    ComposeText(
                        text = balance.accountNumber,
                        textStyle = defaultComposeTextStyle().copy(
                            textColor = MaterialTheme.colors.onBackground,
                            typography = MaterialTheme.typography.caption,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun YourOfferCard() {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_padding)),
    ) {
        ComposeText(
            text = stringResource(id = R.string.your_offer),
            textStyle = defaultComposeTextStyle().copy(
                textColor = colorResource(id = R.color.grey),
                typography = MaterialTheme.typography.h3,
            ),
        )

        Surface(
            color = MaterialTheme.colors.surface,
            shape = MaterialTheme.shapes.medium,
            elevation = dimensionResource(id = R.dimen.default_elevation),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.medium_padding)),
            ) {
                // no-op
            }
        }
    }
}

@Composable
private fun AdditionalInternetPackageList() {
    // TODO("Not yet implemented")
}

@Composable
private fun TopBar(userInfo: UserInfo) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background,
        elevation = dimensionResource(id = R.dimen.default_elevation),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(dimensionResource(id = R.dimen.medium_padding)),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_button_size))
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.surface),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
                    painter = painterResource(id = R.drawable.ic_person),
                    tint = colorResource(id = R.color.accent),
                    contentDescription = null,
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.tiny_padding)),
            ) {
                ComposeText(
                    text = userInfo.name,
                    textStyle = defaultComposeTextStyle().copy(
                        textColor = MaterialTheme.colors.onBackground,
                    ),
                )
                ComposeText(
                    text = userInfo.phoneNumber.orEmpty(),
                    textStyle = defaultComposeTextStyle().copy(
                        textColor = colorResource(id = R.color.grey),
                    ),
                )
            }
            Box(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.icon_button_size))
                    .clip(CircleShape)
                    .background(MaterialTheme.colors.surface)
                    .clickable { },
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
                    painter = painterResource(id = R.drawable.ic_settings),
                    tint = colorResource(id = R.color.accent),
                    contentDescription = null,
                )
            }
        }
    }
}
