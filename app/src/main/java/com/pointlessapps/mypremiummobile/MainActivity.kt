package com.pointlessapps.mypremiummobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.core.view.WindowCompat
import com.pointlessapps.mypremiummobile.compose.NavHost
import com.pointlessapps.mypremiummobile.compose.ui.components.ComposeSnackbar
import com.pointlessapps.mypremiummobile.compose.ui.components.ComposeSnackbarHostState
import com.pointlessapps.mypremiummobile.compose.ui.theme.ProjectTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        setContent {
            ProjectTheme {
                val context = LocalContext.current
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()
                val audiSnackbarHostState = ComposeSnackbarHostState { message, duration ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = context.getString(message),
                            duration = duration,
                        )
                    }
                }

                CompositionLocalProvider(
                    LocalTextSelectionColors provides TextSelectionColors(
                        handleColor = MaterialTheme.colors.primary,
                        backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.4f),
                    ),
                    LocalSnackbarHostState provides audiSnackbarHostState,
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background,
                    ) {
                        NavHost()

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .systemBarsPadding()
                                .imePadding()
                                .padding(dimensionResource(id = R.dimen.medium_padding)),
                            contentAlignment = Alignment.BottomCenter,
                        ) {
                            SnackbarHost(hostState = snackbarHostState) {
                                ComposeSnackbar(message = it.message)
                            }
                        }
                    }
                }
            }
        }
    }
}

internal val LocalSnackbarHostState = compositionLocalOf<ComposeSnackbarHostState> {
    error("No ComposeSnackbarHostState")
}
