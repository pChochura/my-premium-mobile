package com.pointlessapps.mypremiummobile.compose.dashboard.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserOffer(
    val tariff: String = "",
    val callsMobile: String = "",
    val callsLandLine: String = "",
    val sms: String = "",
    val mms: String = "",
) : Parcelable
