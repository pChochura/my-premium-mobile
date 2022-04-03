package com.pointlessapps.mypremiummobile.compose.ui.theme

import android.os.Parcelable
import com.pointlessapps.mypremiummobile.compose.model.UserInfo
import kotlinx.parcelize.Parcelize

internal sealed interface Route : Parcelable {
    @Parcelize
    object Login : Route

    @Parcelize
    data class Dashboard(val userInfo: UserInfo) : Route
}
