package com.pointlessapps.mypremiummobile.compose.dashboard.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhoneNumber(
    val id: String = "",
    val number: String = "",
) : Parcelable
