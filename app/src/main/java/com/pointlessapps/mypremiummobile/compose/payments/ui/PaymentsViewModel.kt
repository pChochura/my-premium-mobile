package com.pointlessapps.mypremiummobile.compose.payments.ui

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pointlessapps.mypremiummobile.compose.dashboard.model.PhoneNumber
import com.pointlessapps.mypremiummobile.compose.model.Balance
import com.pointlessapps.mypremiummobile.compose.model.UserInfo
import com.pointlessapps.mypremiummobile.compose.payments.model.Invoice
import com.pointlessapps.mypremiummobile.compose.payments.model.PaymentsModel
import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.InvoiceResponse
import com.pointlessapps.mypremiummobile.domain.auth.usecase.GetUserNameUseCase
import com.pointlessapps.mypremiummobile.domain.payments.usecase.*
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetUserPhoneNumbersUseCase
import com.pointlessapps.mypremiummobile.domain.utils.DateFormatter
import com.pointlessapps.mypremiummobile.domain.utils.NumberFormatter
import com.pointlessapps.mypremiummobile.errors.AuthorizationTokenExpiredException
import com.pointlessapps.mypremiummobile.utils.errors.ErrorHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*

internal data class PaymentsState(
    val userInfo: UserInfo = UserInfo(),
    val balance: Balance = Balance(),
    val invoices: List<Invoice> = emptyList(),
    val isLoading: Boolean = false,
)

internal sealed interface PaymentsEvent {
    object MoveToLoginScreen : PaymentsEvent
    data class OpenFile(val uri: Uri) : PaymentsEvent
    data class OpenUrl(val uri: Uri) : PaymentsEvent
    data class ShowErrorMessage(@StringRes val message: Int) : PaymentsEvent
}

internal class PaymentsViewModel(
    private val errorHandler: ErrorHandler,
    getUserNameUseCase: GetUserNameUseCase,
    getUserPhoneNumbersUseCase: GetUserPhoneNumbersUseCase,
    getPaymentAmountUseCase: GetPaymentAmountUseCase,
    getInvoicesUseCase: GetInvoicesUseCase,
    private val downloadInvoiceUseCase: DownloadInvoiceUseCase,
    private val downloadBillingUseCase: DownloadBillingUseCase,
    private val getPayWithPayUUrlUseCase: GetPayWithPayUUrlUseCase,
    private val dateFormatter: DateFormatter,
    private val numberFormatter: NumberFormatter,
) : ViewModel() {

    companion object {
        private const val THREE_MONTHS = 3
    }

    private val eventChannel = Channel<PaymentsEvent>(Channel.RENDEZVOUS)
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(PaymentsState())

    init {
        val today = Calendar.getInstance().time
        val threeMonthsAgo =
            Calendar.getInstance().apply { add(Calendar.MONTH, -THREE_MONTHS) }.time

        combine(
            getUserPhoneNumbersUseCase.prepare(),
            getUserNameUseCase.prepare(),
            getPaymentAmountUseCase.prepare(),
            getInvoicesUseCase.prepare(threeMonthsAgo, today),
        ) { phoneNumbers, userInfo, paymentAmount, invoices ->
            val phoneNumber = requireNotNull(phoneNumbers.find { it.isMain }).run {
                PhoneNumber(id = id, number = phoneNumber)
            }

            buildModel(
                userInfo = userInfo,
                phoneNumber = phoneNumber,
                paymentAmount = paymentAmount,
                invoices = invoices,
            )
        }.take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .onEach { (userInfo, balance, invoices) ->
                state = state.copy(
                    userInfo = userInfo,
                    balance = balance,
                    invoices = invoices,
                    isLoading = false,
                )
            }
            .catch { throwable ->
                Timber.e(throwable)

                if (throwable is AuthorizationTokenExpiredException) {
                    eventChannel.send(PaymentsEvent.MoveToLoginScreen)
                }

                state = state.copy(isLoading = false)
                eventChannel.send(
                    PaymentsEvent.ShowErrorMessage(
                        errorHandler.mapThrowableToErrorMessage(throwable),
                    ),
                )
            }
            .launchIn(viewModelScope)
    }

    private fun buildModel(
        userInfo: UserInfoResponse,
        phoneNumber: PhoneNumber,
        paymentAmount: Float,
        invoices: List<InvoiceResponse>,
    ) = PaymentsModel(
        userInfo = UserInfo(
            email = userInfo.email,
            name = userInfo.name,
            phoneNumber = phoneNumber.number,
        ),
        balance = Balance(
            balance = numberFormatter.toFloatString(paymentAmount),
        ),
        invoices = invoices.map {
            Invoice(
                invoiceNumber = it.invoiceNumber,
                invoiceDate = dateFormatter.formatDate(it.invoiceDate),
                paymentDate = dateFormatter.formatDate(it.paymentDate),
                amount = it.amount,
                isPaid = it.status,
            )
        },
    )

    fun downloadInvoice(invoice: Invoice) = executeDownloadFile(
        downloadInvoiceUseCase.prepare(invoice.invoiceNumber),
    )

    fun downloadBilling(invoice: Invoice) = executeDownloadFile(
        downloadBillingUseCase.prepare(invoice.invoiceNumber),
    )

    private fun executeDownloadFile(flow: Flow<String>) {
        flow.take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .onEach { fileUri ->
                state = state.copy(isLoading = false)
                eventChannel.send(PaymentsEvent.OpenFile(Uri.parse(fileUri)))
            }
            .catch { throwable ->
                Timber.e(throwable)

                state = state.copy(isLoading = false)
                eventChannel.send(
                    PaymentsEvent.ShowErrorMessage(
                        errorHandler.mapThrowableToErrorMessage(throwable),
                    ),
                )
            }
            .launchIn(viewModelScope)
    }

    fun payWithPayU() {
        getPayWithPayUUrlUseCase
            .prepare(numberFormatter.toFloat(state.balance.balance))
            .take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .onEach { url ->
                state = state.copy(isLoading = false)
                eventChannel.send(PaymentsEvent.OpenUrl(Uri.parse(url)))
            }
            .catch { throwable ->
                Timber.e(throwable)

                state = state.copy(isLoading = false)
                eventChannel.send(
                    PaymentsEvent.ShowErrorMessage(
                        errorHandler.mapThrowableToErrorMessage(throwable),
                    ),
                )
            }
            .launchIn(viewModelScope)
    }
}
