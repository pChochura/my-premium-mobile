package com.pointlessapps.mypremiummobile

import androidx.lifecycle.ViewModel
import com.pointlessapps.mypremiummobile.compose.ui.theme.Route
import com.pointlessapps.mypremiummobile.domain.auth.usecase.IsLoggedInUseCase

internal class MainViewModel(
    private val isLoggedInUseCase: IsLoggedInUseCase,
) : ViewModel() {

    fun getStartDestination() = when {
        isLoggedInUseCase() -> Route.Dashboard
        else -> Route.Login
    }
}
