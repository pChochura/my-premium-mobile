package com.pointlessapps.mypremiummobile.compose.payments.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeliveryMethod(
    val id: Int,
    val name: String,
    val price: String?,
    val enabled: Boolean,
) : Parcelable
