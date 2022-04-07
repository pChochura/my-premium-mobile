package com.pointlessapps.mypremiummobile.compose.payments.ui

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pointlessapps.mypremiummobile.compose.model.Balance
import com.pointlessapps.mypremiummobile.compose.model.UserInfo
import com.pointlessapps.mypremiummobile.compose.payments.model.DeliveryMethod
import com.pointlessapps.mypremiummobile.compose.payments.model.Invoice
import com.pointlessapps.mypremiummobile.compose.payments.model.Payment
import com.pointlessapps.mypremiummobile.compose.payments.model.PaymentsModel
import com.pointlessapps.mypremiummobile.datasource.auth.dto.UserInfoResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.DeliveryMethodResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.InvoiceResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.PaymentResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.PhoneNumberResponse
import com.pointlessapps.mypremiummobile.domain.payments.usecase.*
import com.pointlessapps.mypremiummobile.domain.usecase.GetPaymentsModelUseCase
import com.pointlessapps.mypremiummobile.domain.utils.DateFormatter
import com.pointlessapps.mypremiummobile.domain.utils.NumberFormatter
import com.pointlessapps.mypremiummobile.errors.AuthorizationTokenExpiredException
import com.pointlessapps.mypremiummobile.utils.errors.ErrorHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

internal data class PaymentsState(
    val userInfo: UserInfo = UserInfo(),
    val balance: Balance = Balance(),
    val invoices: List<Invoice> = emptyList(),
    val payments: List<Payment> = emptyList(),
    val deliveryMethods: List<DeliveryMethod> = emptyList(),
    val isLoading: Boolean = false,
)

internal sealed interface PaymentsEvent {
    object MoveToLoginScreen : PaymentsEvent
    data class OpenFile(val uri: Uri) : PaymentsEvent
    data class OpenUrl(val uri: Uri) : PaymentsEvent
    data class ShowErrorMessage(@StringRes val message: Int) : PaymentsEvent
    data class ShowConfirmationDialog(
        val state: Boolean,
        val name: String,
        val number: String,
        val onConfirm: () -> Unit,
    ) : PaymentsEvent
}

internal class PaymentsViewModel(
    private val errorHandler: ErrorHandler,
    getPaymentsModelUseCase: GetPaymentsModelUseCase,
    private val downloadInvoiceUseCase: DownloadInvoiceUseCase,
    private val downloadBillingUseCase: DownloadBillingUseCase,
    private val getPayWithPayUUrlUseCase: GetPayWithPayUUrlUseCase,
    private val changeDeliveryMethodUseCase: ChangeDeliveryMethodUseCase,
    private val getDeliveryMethodsUseCase: GetDeliveryMethodsUseCase,
    private val dateFormatter: DateFormatter,
    private val numberFormatter: NumberFormatter,
) : ViewModel() {

    private val eventChannel = Channel<PaymentsEvent>(Channel.RENDEZVOUS)
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(PaymentsState())

    init {
        getPaymentsModelUseCase()
            .take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .onEach { model ->
                val (userInfo, balance, invoices, payments, deliveryMethods) = buildModel(
                    phoneNumber = model.phoneNumber,
                    userInfo = model.userInfo,
                    paymentAmount = model.paymentAmount,
                    invoices = model.invoices,
                    payments = model.payments,
                    deliveryMethods = model.deliveryMethods,
                )
                state = state.copy(
                    userInfo = userInfo,
                    balance = balance,
                    invoices = invoices,
                    payments = payments,
                    deliveryMethods = deliveryMethods,
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
        phoneNumber: PhoneNumberResponse,
        paymentAmount: Float,
        invoices: List<InvoiceResponse>,
        payments: List<PaymentResponse>,
        deliveryMethods: List<DeliveryMethodResponse>,
    ) = PaymentsModel(
        userInfo = UserInfo(
            email = userInfo.email,
            name = userInfo.name,
            phoneNumber = phoneNumber.phoneNumber,
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
        payments = payments.map {
            Payment(
                title = it.title,
                amount = numberFormatter.toFloatString(it.amount),
                paymentDate = dateFormatter.formatDate(it.paymentDate),
                postingDate = dateFormatter.formatDate(it.postingDate),
            )
        },
        deliveryMethods = mapDeliveryMethods(deliveryMethods),
    )

    fun downloadInvoice(invoice: Invoice) = executeDownloadFile(
        downloadInvoiceUseCase(invoice.invoiceNumber),
    )

    fun downloadBilling(invoice: Invoice) = executeDownloadFile(
        downloadBillingUseCase(invoice.invoiceNumber),
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
        getPayWithPayUUrlUseCase(numberFormatter.toFloat(state.balance.balance))
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

    fun checkDeliveryMethod(deliveryMethod: DeliveryMethod) {
        viewModelScope.launch {
            eventChannel.send(
                PaymentsEvent.ShowConfirmationDialog(
                    state = !deliveryMethod.enabled,
                    name = deliveryMethod.name,
                    number = requireNotNull(state.userInfo.phoneNumber),
                    onConfirm = {
                        changeDeliveryMethod(deliveryMethod.id, !deliveryMethod.enabled)
                    },
                ),
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun changeDeliveryMethod(id: Int, enabled: Boolean) {
        changeDeliveryMethodUseCase(id, enabled)
            .take(1)
            .onStart {
                state = state.copy(isLoading = true)
            }
            .flatMapLatest {
                getDeliveryMethodsUseCase()
            }
            .onEach { deliveryMethods ->
                state = state.copy(
                    isLoading = false,
                    deliveryMethods = mapDeliveryMethods(deliveryMethods),
                )
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

    private fun mapDeliveryMethods(deliveryMethods: List<DeliveryMethodResponse>) =
        deliveryMethods.map {
            DeliveryMethod(
                id = it.id,
                name = it.method,
                price = it.price.takeIf { number -> numberFormatter.toFloat(number) != 0f },
                enabled = it.status,
            )
        }
}
