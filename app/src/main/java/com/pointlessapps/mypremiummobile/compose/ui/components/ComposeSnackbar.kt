package com.pointlessapps.mypremiummobile.compose.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import com.pointlessapps.mypremiummobile.R

@Composable
internal fun ComposeSnackbar(message: String) {
    Snackbar(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        backgroundColor = colorResource(id = R.color.black),
    ) {
        ComposeText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.small_padding)),
            text = message,
            textStyle = defaultComposeTextStyle().copy(
                textColor = colorResource(id = R.color.white),
                typography = MaterialTheme.typography.body1,
            ),
        )
    }
}

internal class ComposeSnackbarHostState(private val onShowSnackbarListener: (Int, SnackbarDuration) -> Unit) {

    fun showSnackbar(@StringRes message: Int, duration: SnackbarDuration = SnackbarDuration.Short) =
        onShowSnackbarListener(message, duration)
}
