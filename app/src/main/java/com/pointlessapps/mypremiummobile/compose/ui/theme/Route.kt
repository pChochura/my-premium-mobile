package com.pointlessapps.mypremiummobile.compose.ui.theme

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

internal sealed interface Route : Parcelable {
    @Parcelize
    object Login : Route

    @Parcelize
    object Dashboard : Route
}
