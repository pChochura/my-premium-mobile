package com.pointlessapps.mypremiummobile.compose.dashboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import com.pointlessapps.mypremiummobile.LocalSnackbarHostState
import com.pointlessapps.mypremiummobile.R
import com.pointlessapps.mypremiummobile.compose.dashboard.model.InternetPackage
import com.pointlessapps.mypremiummobile.compose.dashboard.model.InternetPackageStatus
import com.pointlessapps.mypremiummobile.compose.dashboard.model.UserOffer
import com.pointlessapps.mypremiummobile.compose.model.Balance
import com.pointlessapps.mypremiummobile.compose.ui.components.*
import org.koin.androidx.compose.getViewModel

@Composable
internal fun DashboardScreen(
    viewModel: DashboardViewModel = getViewModel(),
    onShowLogin: () -> Unit,
    onShowPayments: () -> Unit,
) {
    val snackbarHost = LocalSnackbarHostState.current
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                DashboardEvent.MoveToLoginScreen ->
                    onShowLogin()
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
                .padding(vertical = dimensionResource(id = R.dimen.medium_padding))
                .navigationBarsPadding()
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.big_padding)),
        ) {
            AccountBalanceCard(
                balance = viewModel.state.balance,
                onShowPaymentDetails = onShowPayments,
            )
            YourOfferCard(viewModel.state.userOffer, viewModel.state.internetPackageStatus)
            AdditionalInternetPackageList(viewModel.state.internetPackages)
        }
    }
}

@Composable
private fun AccountBalanceCard(balance: Balance, onShowPaymentDetails: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.medium_padding)),
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
                    .clip(CircleShape)
                    .clickable(onClick = onShowPaymentDetails),
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
                    text = stringResource(id = R.string.amount_value, balance.balance),
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
private fun YourOfferCard(userOffer: UserOffer, internetPackageStatus: InternetPackageStatus) {
    Column(
        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.medium_padding)),
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.medium_padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding)),
            ) {
                YourOfferRow(
                    label = stringResource(id = R.string.tariff),
                    value = userOffer.tariff,
                )
                YourOfferRow(
                    label = stringResource(id = R.string.calls_to_mobile),
                    value = userOffer.callsMobile,
                )
                YourOfferRow(
                    label = stringResource(id = R.string.calls_to_landline),
                    value = userOffer.callsLandLine,
                )
                YourOfferRow(
                    label = stringResource(id = R.string.sms),
                    value = userOffer.sms,
                )
                YourOfferRow(
                    label = stringResource(id = R.string.mms),
                    value = userOffer.mms,
                )
                YourOfferRow(
                    label = stringResource(id = R.string.internet),
                    value = stringResource(
                        id = R.string.internet_status,
                        internetPackageStatus.currentStatus,
                        internetPackageStatus.limit,
                    ),
                )
            }
        }
    }
}

@Composable
private fun YourOfferRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ComposeText(
            modifier = Modifier.weight(1f),
            text = label,
            textStyle = defaultComposeTextStyle().copy(
                textColor = MaterialTheme.colors.onSurface,
                typography = MaterialTheme.typography.caption,
            ),
        )

        ComposeText(
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.accent_variant),
                    shape = MaterialTheme.shapes.small,
                )
                .padding(
                    vertical = dimensionResource(id = R.dimen.tiny_padding),
                    horizontal = dimensionResource(id = R.dimen.small_padding),
                ),
            text = value,
            textStyle = defaultComposeTextStyle().copy(
                textColor = MaterialTheme.colors.onSurface,
                typography = MaterialTheme.typography.caption,
            ),
        )
    }
}

@Composable
private fun AdditionalInternetPackageList(internetPackages: List<InternetPackage>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_padding)),
    ) {
        ComposeText(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.medium_padding)),
            text = stringResource(id = R.string.additional_internet_package),
            textStyle = defaultComposeTextStyle().copy(
                textColor = colorResource(id = R.color.grey),
                typography = MaterialTheme.typography.h3,
            ),
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.medium_padding)),
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.medium_padding),
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(internetPackages, key = { it }) { internetPackage ->
                Surface(
                    modifier = Modifier.clickable { },
                    color = MaterialTheme.colors.surface,
                    shape = MaterialTheme.shapes.medium,
                    elevation = dimensionResource(id = R.dimen.default_elevation),
                ) {
                    Column(
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.card_size))
                            .padding(dimensionResource(id = R.dimen.medium_padding)),
                        verticalArrangement = Arrangement.spacedBy(
                            dimensionResource(id = R.dimen.small_padding),
                            Alignment.CenterVertically,
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ComposeText(
                            text = internetPackage.name,
                            textStyle = defaultComposeTextStyle().copy(
                                textColor = MaterialTheme.colors.onSurface,
                                typography = MaterialTheme.typography.caption,
                                textAlign = TextAlign.Center,
                            ),
                        )
                        ComposeText(
                            text = stringResource(
                                id = R.string.amount_value,
                                internetPackage.amount,
                            ),
                            textStyle = defaultComposeTextStyle().copy(
                                textColor = colorResource(id = R.color.accent),
                                typography = MaterialTheme.typography.button,
                                textAlign = TextAlign.Center,
                            ),
                        )
                    }
                }
            }
        }
    }
}
