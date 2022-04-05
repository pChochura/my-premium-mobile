package com.pointlessapps.mypremiummobile.compose.ui.theme

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Suppress("PROPERTY_WONT_BE_SERIALIZED")
internal sealed class Route : Parcelable {

    @IgnoredOnParcel
    open val showNavigationBar: Boolean = true

    @Parcelize
    object Login : Route() {
        override val showNavigationBar = false
    }

    @Parcelize
    object LoginTwoFactor : Route() {
        override val showNavigationBar = false
    }

    @Parcelize
    object Dashboard : Route()

    @Parcelize
    object Payments : Route()

    @Parcelize
    object Services : Route()

    @Parcelize
    object Documents : Route()

    @Parcelize
    object Help : Route()
}
