package com.pointlessapps.mypremiummobile.compose

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.pointlessapps.mypremiummobile.compose.login.LoginScreen
import com.pointlessapps.mypremiummobile.compose.ui.theme.Route
import dev.olshevski.navigation.reimagined.*

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
                onShowNextScreen = {
                    navController.navigate(Route.Dashboard)
                },
            )
            Route.Dashboard -> {
                println("LOG!, inside dashboard!")
            }
        }
    }
}
