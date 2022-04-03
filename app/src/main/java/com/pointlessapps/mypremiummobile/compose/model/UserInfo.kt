package com.pointlessapps.mypremiummobile.compose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val email: String,
    val name: String,
    val phoneNumber: String? = null,
) : Parcelable
