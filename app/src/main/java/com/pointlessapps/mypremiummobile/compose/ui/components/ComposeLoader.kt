package com.pointlessapps.mypremiummobile.compose.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.zIndex
import com.pointlessapps.mypremiummobile.R

private const val SCRIM_ALPHA = 0.9f

@Composable
internal fun ComposeLoader(enabled: Boolean) {
    val focusManager = LocalFocusManager.current
    LaunchedEffect(enabled) {
        if (enabled) {
            focusManager.clearFocus(true)
        }
    }

    AnimatedVisibility(
        modifier = Modifier.zIndex(1f),
        visible = enabled,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface.copy(alpha = SCRIM_ALPHA))
                .systemBarsPadding()
                .imePadding(),
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colorResource(id = R.color.accent),
            )
        }
    }
}
