package com.pointlessapps.mypremiummobile.compose

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.pointlessapps.mypremiummobile.compose.dashboard.DashboardScreen
import com.pointlessapps.mypremiummobile.compose.login.LoginScreen
import com.pointlessapps.mypremiummobile.compose.ui.theme.Route
import dev.olshevski.navigation.reimagined.*
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalAnimationApi::class)
@Composable
internal fun NavHost(
    navController: NavController<Route> = rememberNavController(
        startDestination = Route.Login,
    ),
) {
    NavBackHandler(navController = navController)
    AnimatedNavHost(controller = navController) {
        when (it) {
            Route.Login -> LoginScreen(
                onShowDashboard = { userInfo ->
                    navController.navigate(Route.Dashboard(userInfo))
                },
            )
            is Route.Dashboard -> DashboardScreen(
                viewModel = getViewModel {
                    parametersOf(it.userInfo)
                }
            )
        }
    }
}
