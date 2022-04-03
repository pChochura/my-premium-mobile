package com.pointlessapps.mypremiummobile.compose

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.pointlessapps.mypremiummobile.compose.dashboard.DashboardScreen
import com.pointlessapps.mypremiummobile.compose.login.LoginScreen
import com.pointlessapps.mypremiummobile.compose.ui.theme.Route
import dev.olshevski.navigation.reimagined.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun NavHost(navController: NavController<Route>) {
    BackHandler(enabled = navController.backstack.entries.size > 1) {
        if (navController.backstack.entries.last().destination == Route.Dashboard) {
            navController.moveToTop { it != Route.Dashboard }
        } else {
            navController.pop()
            navController.moveToTop { it != Route.Dashboard }
        }
    }
    AnimatedNavHost(controller = navController) {
        when (it) {
            Route.Login -> LoginScreen(
                onShowDashboard = {
                    navController.popAll()
                    navController.navigate(Route.Dashboard)
                },
            )
            Route.Dashboard -> DashboardScreen(
                onShowLogin = {
                    navController.popAll()
                    navController.navigate(Route.Login)
                },
            )
        }
    }
}

internal fun <T> NavController<T>.navigateIfPossible(destination: T) {
    if (!moveToTop { it == destination }) {
        navigate(destination)
    }
}
