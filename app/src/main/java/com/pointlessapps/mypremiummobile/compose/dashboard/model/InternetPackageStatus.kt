package com.pointlessapps.mypremiummobile.compose.dashboard.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class InternetPackageStatus(
    val currentStatus: Float = 0f,
    val limit: Float = 0f,
    val limitDate: Date = Date(),
) : Parcelable
