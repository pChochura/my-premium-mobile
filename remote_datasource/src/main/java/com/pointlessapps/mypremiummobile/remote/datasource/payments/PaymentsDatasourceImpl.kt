package com.pointlessapps.mypremiummobile.remote.datasource.payments

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.pointlessapps.mypremiummobile.datasource.payments.PaymentsDatasource
import com.pointlessapps.mypremiummobile.datasource.payments.dto.BalanceResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.DeliveryMethodResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.InvoiceResponse
import com.pointlessapps.mypremiummobile.datasource.payments.dto.PaymentResponse
import com.pointlessapps.mypremiummobile.remote.datasource.payments.dto.ChangeDeliveryMethodBodyDto
import com.pointlessapps.mypremiummobile.remote.datasource.payments.dto.DateRangeBodyDto
import com.pointlessapps.mypremiummobile.remote.datasource.payments.dto.PayWithPayUBodyDto
import com.pointlessapps.mypremiummobile.remote.datasource.payments.mapper.toBalanceResponse
import com.pointlessapps.mypremiummobile.remote.datasource.payments.mapper.toDeliveryMethods
import com.pointlessapps.mypremiummobile.remote.datasource.payments.mapper.toInvoicesResponse
import com.pointlessapps.mypremiummobile.remote.datasource.payments.mapper.toPaymentsResponse
import com.pointlessapps.mypremiummobile.remote.datasource.payments.service.PaymentsService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

internal class PaymentsDatasourceImpl(
    private val context: Context,
    private val paymentsService: PaymentsService,
    private val dateParser: SimpleDateFormat,
) : PaymentsDatasource {

    override suspend fun getBalance(): BalanceResponse =
        paymentsService.getBalance().toBalanceResponse()

    override suspend fun getPaymentAmount(): Float =
        paymentsService.getPaymentAmount()

    override suspend fun getInvoices(fromDate: Date, toDate: Date): List<InvoiceResponse> =
        paymentsService.getInvoices(
            DateRangeBodyDto(
                startDate = dateParser.format(fromDate),
                endDate = dateParser.format(toDate),
            ),
        ).toInvoicesResponse(dateParser)

    override suspend fun getPayments(fromDate: Date, toDate: Date): List<PaymentResponse> =
        paymentsService.getPayments(
            DateRangeBodyDto(
                startDate = dateParser.format(fromDate),
                endDate = dateParser.format(toDate),
            ),
        ).toPaymentsResponse(dateParser)

    override suspend fun getDeliveryMethods(): List<DeliveryMethodResponse> =
        paymentsService.getDeliveryMethods().toDeliveryMethods()

    override suspend fun changeDeliveryMethod(deliveryMethodId: Int, state: Boolean) =
        paymentsService.changeDeliveryMethod(
            ChangeDeliveryMethodBodyDto(
                action = state,
                methodId = deliveryMethodId,
            ),
        )

    @Throws(NullPointerException::class)
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun downloadInvoice(invoiceNumber: String): String {
        val invoiceResponse = paymentsService.getInvoiceDocument(
            invoiceNumber.toRequestBody(TEXT_PLAIN_MEDIA_TYPE.toMediaType()),
        )

        val filename = invoiceResponse.headers()[FILENAME_HEADER_NAME]
            ?.substringAfter(FILENAME_PREFIX)
            ?.substringBefore(FILE_EXTENSION) ?: throw NullPointerException()

        val body = invoiceResponse.body() ?: throw NullPointerException()
        return saveFile(filename, body.bytes())
    }

    @Throws(NullPointerException::class)
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun downloadBilling(invoiceNumber: String): String {
        val billingResponse = paymentsService.getBillingDocument(
            invoiceNumber.toRequestBody(TEXT_PLAIN_MEDIA_TYPE.toMediaType()),
        )

        val filename = billingResponse.headers()[FILENAME_HEADER_NAME]
            ?.substringAfter(FILENAME_PREFIX)
            ?.substringBefore(FILE_EXTENSION) ?: throw NullPointerException()

        val body = billingResponse.body() ?: throw NullPointerException()
        return saveFile(filename, body.bytes())
    }

    @Throws(NullPointerException::class)
    private fun saveFile(filename: String, bytes: ByteArray): String {
        val downloadedFile = File(
            "${context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.path}${File.separator}" +
                    "$filename$FILE_EXTENSION",
        )

        downloadedFile.writeBytes(bytes)
        return Uri.fromFile(downloadedFile).path ?: throw NullPointerException()
    }

    override suspend fun getPayWithPayUUrl(amount: Float) =
        paymentsService.getPayWithPayUUrl(PayWithPayUBodyDto(amount))

    companion object {
        private const val TEXT_PLAIN_MEDIA_TYPE = "text/plain"
        private const val FILENAME_HEADER_NAME = "content-disposition"
        private const val FILENAME_PREFIX = "filename="
        private const val FILE_EXTENSION = ".pdf"
    }
}
