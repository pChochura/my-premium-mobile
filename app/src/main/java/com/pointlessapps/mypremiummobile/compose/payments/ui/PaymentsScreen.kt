package com.pointlessapps.mypremiummobile.compose.payments.ui

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.pointlessapps.mypremiummobile.LocalSnackbarHostState
import com.pointlessapps.mypremiummobile.R
import com.pointlessapps.mypremiummobile.compose.model.Balance
import com.pointlessapps.mypremiummobile.compose.payments.model.Invoice
import com.pointlessapps.mypremiummobile.compose.ui.components.*
import org.koin.androidx.compose.getViewModel

private const val MAX_ITEMS_DISPLAYED = 3

@Composable
internal fun PaymentsScreen(
    viewModel: PaymentsViewModel = getViewModel(),
    onShowLogin: () -> Unit,
) {
    val context = LocalContext.current
    val snackbarHost = LocalSnackbarHostState.current
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                PaymentsEvent.MoveToLoginScreen ->
                    onShowLogin()
                is PaymentsEvent.ShowErrorMessage ->
                    snackbarHost.showSnackbar(event.message)
                is PaymentsEvent.OpenFile -> context.startActivity(
                    Intent(Intent.ACTION_VIEW, event.uri).apply {
                        type = "application/pdf"
                    },
                )
                is PaymentsEvent.OpenUrl -> context.startActivity(
                    Intent(Intent.ACTION_VIEW, event.uri),
                )
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
            AccountBalanceCard(
                balance = viewModel.state.balance,
                onPayWithPayU = viewModel::payWithPayU,
            )
            InvoicesCard(
                invoices = viewModel.state.invoices,
                onDownloadInvoice = viewModel::downloadInvoice,
                onDownloadBilling = viewModel::downloadBilling,
            )
        }
    }
}

@Composable
private fun AccountBalanceCard(
    balance: Balance,
    onPayWithPayU: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.primary,
        shape = MaterialTheme.shapes.medium,
        elevation = dimensionResource(id = R.dimen.default_elevation),
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ComposeText(
                text = stringResource(id = R.string.account_balance),
                textStyle = defaultComposeTextStyle().copy(
                    textColor = colorResource(id = R.color.accent_variant),
                    textAlign = TextAlign.Center,
                ),
            )
            ComposeText(
                text = stringResource(id = R.string.amount_value, balance.balance),
                textStyle = defaultComposeTextStyle().copy(
                    textColor = colorResource(id = R.color.accent_variant),
                    typography = MaterialTheme.typography.h1,
                    textAlign = TextAlign.Center,
                ),
            )

            ComposeText(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.small_padding)),
                text = stringResource(id = R.string.negative_value_means_overpayment),
                textStyle = defaultComposeTextStyle().copy(
                    textColor = colorResource(id = R.color.accent_variant),
                    typography = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center,
                ),
            )

            ComposeButton(
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.small_padding))
                    .fillMaxWidth(),
                text = stringResource(id = R.string.pay_with_payu),
                onClick = onPayWithPayU,
                buttonStyle = defaultComposeButtonStyle().copy(
                    backgroundColor = colorResource(id = R.color.accent),
                ),
            )
        }
    }
}

@Composable
private fun InvoicesCard(
    invoices: List<Invoice>,
    onDownloadInvoice: (Invoice) -> Unit,
    onDownloadBilling: (Invoice) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_padding)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ComposeText(
                text = stringResource(id = R.string.invoices),
                textStyle = defaultComposeTextStyle().copy(
                    textColor = colorResource(id = R.color.grey),
                    typography = MaterialTheme.typography.h3,
                ),
            )

            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .clickable { /*TODO*/ }
                    .padding(horizontal = dimensionResource(id = R.dimen.tiny_padding)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ComposeText(
                    text = stringResource(id = R.string.more),
                    textStyle = defaultComposeTextStyle().copy(
                        textColor = colorResource(id = R.color.grey),
                        typography = MaterialTheme.typography.body1,
                        textAlign = TextAlign.End,
                    ),
                )
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.icon_size)),
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = colorResource(id = R.color.grey),
                )
            }
        }

        Surface(
            color = MaterialTheme.colors.surface,
            shape = MaterialTheme.shapes.medium,
            elevation = dimensionResource(id = R.dimen.default_elevation),
        ) {
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.medium_padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_padding)),
            ) {
                val items = invoices.take(MAX_ITEMS_DISPLAYED)
                items.forEachIndexed { index, item ->
                    InvoiceRow(
                        invoice = item,
                        onDownloadInvoice = onDownloadInvoice,
                        onDownloadBilling = onDownloadBilling,
                    )

                    if (index != items.lastIndex) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(dimensionResource(id = R.dimen.divider_height))
                                .background(colorResource(id = R.color.accent_variant)),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InvoiceRow(
    invoice: Invoice,
    onDownloadInvoice: (Invoice) -> Unit,
    onDownloadBilling: (Invoice) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            ComposeText(
                text = invoice.invoiceNumber,
                textStyle = defaultComposeTextStyle().copy(
                    textColor = MaterialTheme.colors.onSurface,
                    typography = MaterialTheme.typography.body1,
                ),
            )
            ComposeText(
                text = invoice.invoiceDate,
                textStyle = defaultComposeTextStyle().copy(
                    textColor = MaterialTheme.colors.onSurface,
                    typography = MaterialTheme.typography.caption,
                    textAlign = TextAlign.End,
                ),
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.tiny_padding))) {
            ComposeText(
                text = stringResource(id = R.string.due),
                textStyle = defaultComposeTextStyle().copy(
                    textColor = MaterialTheme.colors.onSurface,
                    typography = MaterialTheme.typography.caption,
                ),
            )
            ComposeText(
                text = invoice.paymentDate,
                textStyle = defaultComposeTextStyle().copy(
                    textColor = colorResource(id = R.color.accent),
                    typography = MaterialTheme.typography.caption,
                ),
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_padding))) {
            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(colorResource(id = R.color.accent_variant))
                    .clickable { onDownloadInvoice(invoice) }
                    .padding(
                        vertical = dimensionResource(id = R.dimen.tiny_padding),
                        horizontal = dimensionResource(id = R.dimen.small_padding),
                    ),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.tiny_padding)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.small_icon_size)),
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface,
                )
                ComposeText(
                    text = stringResource(id = R.string.invoice),
                    textStyle = defaultComposeTextStyle().copy(
                        textColor = MaterialTheme.colors.onSurface,
                        typography = MaterialTheme.typography.caption,
                    ),
                )
            }
            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(colorResource(id = R.color.accent_variant))
                    .clickable { onDownloadBilling(invoice) }
                    .padding(
                        vertical = dimensionResource(id = R.dimen.tiny_padding),
                        horizontal = dimensionResource(id = R.dimen.small_padding),
                    ),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.tiny_padding)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier.size(dimensionResource(id = R.dimen.small_icon_size)),
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface,
                )
                ComposeText(
                    text = stringResource(id = R.string.billing),
                    textStyle = defaultComposeTextStyle().copy(
                        textColor = MaterialTheme.colors.onSurface,
                        typography = MaterialTheme.typography.caption,
                    ),
                )
            }
        }
    }
}
