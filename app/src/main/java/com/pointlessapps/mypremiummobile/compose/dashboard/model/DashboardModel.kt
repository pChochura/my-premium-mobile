package com.pointlessapps.mypremiummobile.compose.dashboard.model

import android.os.Parcelable
import com.pointlessapps.mypremiummobile.compose.model.Balance
import com.pointlessapps.mypremiummobile.compose.model.UserInfo
import kotlinx.parcelize.Parcelize

@Parcelize
data class DashboardModel(
    val userInfo: UserInfo,
    val userOffer: UserOffer,
    val balance: Balance,
    val internetPackageStatus: InternetPackageStatus,
    val internetPackages: List<InternetPackage>,
) : Parcelable
