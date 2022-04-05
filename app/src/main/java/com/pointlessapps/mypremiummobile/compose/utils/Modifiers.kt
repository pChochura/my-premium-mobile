package com.pointlessapps.mypremiummobile.compose.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusEvent

/**
 * Basically the same as `onFocusChanged` but ignores the first focus state (always Inactive)
 */
internal fun Modifier.onFocusChanged(
    onFocusGained: () -> Unit = {},
    onFocusLost: () -> Unit = {},
    block: (FocusState) -> Unit = {},
) = composed {
    var focusState by remember { mutableStateOf<FocusState?>(null) }

    onFocusEvent {
        if (focusState != null && it != focusState) {
            if (it.isFocused) {
                onFocusGained()
            } else {
                onFocusLost()
            }
            block(it)
        }
        focusState = it
    }
}

internal inline fun Modifier.conditional(
    condition: Boolean,
    crossinline block: Modifier.() -> Modifier,
) = if (condition) this.block() else this
