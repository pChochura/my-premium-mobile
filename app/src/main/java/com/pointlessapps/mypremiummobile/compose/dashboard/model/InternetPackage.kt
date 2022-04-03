package com.pointlessapps.mypremiummobile.compose.dashboard.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InternetPackage(
    val id: Int = 0,
    val name: String = "",
    val amount: String = "",
) : Parcelable
