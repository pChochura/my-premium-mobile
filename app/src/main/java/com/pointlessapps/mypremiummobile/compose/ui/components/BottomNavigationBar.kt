package com.pointlessapps.mypremiummobile.compose.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pointlessapps.mypremiummobile.R
import com.pointlessapps.mypremiummobile.compose.navigateIfPossible
import com.pointlessapps.mypremiummobile.compose.ui.theme.Route
import dev.olshevski.navigation.reimagined.NavController

@Composable
internal fun BottomNavigationBar(navController: NavController<Route>) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.background,
        elevation = BottomNavigationDefaults.Elevation,
    ) {
        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding(),
            backgroundColor = MaterialTheme.colors.background,
            elevation = 0.dp,
        ) {
            val destination = navController.backstack.entries.lastOrNull()?.destination
            BottomNavigationBarItem(
                isSelected = destination == Route.Dashboard,
                icon = R.drawable.ic_dashboard,
                label = R.string.dashboard,
                onClick = { navController.navigateIfPossible(Route.Dashboard) },
            )
            BottomNavigationBarItem(
                isSelected = destination == Route.Payments,
                icon = R.drawable.ic_card,
                label = R.string.payments,
                onClick = { navController.navigateIfPossible(Route.Payments) },
            )
            BottomNavigationBarItem(
                isSelected = destination == Route.Services,
                icon = R.drawable.ic_sim,
                label = R.string.services,
                onClick = { navController.navigateIfPossible(Route.Services) },
            )
            BottomNavigationBarItem(
                isSelected = destination == Route.Documents,
                icon = R.drawable.ic_document,
                label = R.string.documents,
                onClick = { navController.navigateIfPossible(Route.Documents) },
            )
            BottomNavigationBarItem(
                isSelected = destination == Route.Help,
                icon = R.drawable.ic_headset,
                label = R.string.help,
                onClick = { navController.navigateIfPossible(Route.Help) },
            )
        }
    }
}

@Composable
private fun RowScope.BottomNavigationBarItem(
    isSelected: Boolean,
    @DrawableRes icon: Int,
    @StringRes label: Int,
    onClick: () -> Unit,
) {
    BottomNavigationItem(
        selected = isSelected,
        onClick = onClick,
        icon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = label),
                tint = colorResource(
                    id = if (isSelected) {
                        R.color.accent
                    } else {
                        R.color.grey
                    },
                ),
            )
        },
        label = {
            ComposeText(
                text = stringResource(id = label),
                textStyle = defaultComposeTextStyle().copy(
                    textColor = colorResource(
                        id = if (isSelected) {
                            R.color.accent
                        } else {
                            R.color.grey
                        },
                    ),
                    typography = MaterialTheme.typography.overline,
                    textAlign = TextAlign.Center,
                ),
            )
        },
    )
}
