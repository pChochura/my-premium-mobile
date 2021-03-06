package com.pointlessapps.mypremiummobile.compose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import dev.olshevski.navigation.reimagined.navController

@Composable
internal fun ComposeScaffoldLayout(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    includeBottomNavigationBar: Boolean = true,
    content: @Composable ColumnScope.(PaddingValues) -> Unit,
) {
    SubcomposeLayout { constraints ->
        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight

        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        layout(layoutWidth, layoutHeight) {
            val topBarPlaceables = subcompose(ComposeScaffoldLayoutContent.TopBar, topBar).map {
                it.measure(looseConstraints)
            }

            val topBarHeight = topBarPlaceables.maxByOrNull { it.height }?.height

            val bottomBarPlaceables = subcompose(ComposeScaffoldLayoutContent.BottomBar) {
                if (includeBottomNavigationBar) {
                    BottomNavigationBar(navController(emptyList()))
                }
            }.map { it.measure(looseConstraints) }

            val bottomBarHeight = bottomBarPlaceables.maxByOrNull { it.height }?.height ?: 0

            val bodyContentPlaceables = subcompose(ComposeScaffoldLayoutContent.Content) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(modifier),
                    content = {
                        content(
                            PaddingValues(
                                top = (topBarHeight ?: WindowInsets.statusBars.getTop(
                                    LocalDensity.current,
                                )).toDp(),
                                bottom = bottomBarHeight.toDp(),
                            ),
                        )
                    },
                )
            }.map { it.measure(looseConstraints.copy(maxHeight = layoutHeight)) }

            bodyContentPlaceables.forEach { it.place(0, 0) }
            topBarPlaceables.forEach { it.place(0, 0) }
        }
    }
}

private enum class ComposeScaffoldLayoutContent { TopBar, Content, BottomBar }
