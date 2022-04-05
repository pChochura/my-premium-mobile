package com.pointlessapps.mypremiummobile.compose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.pointlessapps.mypremiummobile.R

private const val BACKDROP_ALPHA = 0.9f

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ComposeDialog(
    title: String,
    content: String,
    primaryButtonText: String,
    onPrimaryButtonClick: () -> Unit,
    onDismissRequest: () -> Unit,
    secondaryButtonText: String? = null,
    onSecondaryButtonClick: (() -> Unit)? = null,
    titleTextStyle: ComposeTextStyle = defaultComposeDialogTitleStyle(),
    contentTextStyle: ComposeTextStyle = defaultComposeDialogContentStyle(),
    primaryButtonStyle: ComposeButtonStyle = defaultComposeDialogPrimaryButtonStyle(),
    secondaryButtonStyle: ComposeButtonStyle = defaultComposeDialogSecondaryButtonModel(),
    dialogStyle: ComposeDialogStyle = defaultComposeDialogStyle(),
) {
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.medium_padding))
                .background(
                    color = dialogStyle.backgroundColor,
                    shape = dialogStyle.shape,
                )
                .padding(
                    vertical = dimensionResource(id = R.dimen.medium_padding),
                    horizontal = dimensionResource(id = R.dimen.big_padding),
                ),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_padding)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ComposeText(
                text = title,
                textStyle = titleTextStyle,
            )
            ComposeText(
                text = content,
                textStyle = contentTextStyle,
            )

            Row(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.medium_padding)),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.medium_padding)),
            ) {
                secondaryButtonText?.let {
                    ComposeButton(
                        text = secondaryButtonText,
                        onClick = { onSecondaryButtonClick?.invoke() },
                        buttonStyle = secondaryButtonStyle,
                    )
                }
                ComposeButton(
                    text = primaryButtonText,
                    onClick = onPrimaryButtonClick,
                    buttonStyle = primaryButtonStyle,
                )
            }
        }
    }
}

@Composable
internal fun defaultComposeDialogTitleStyle() = defaultComposeTextStyle().copy(
    textColor = colorResource(id = R.color.accent_variant),
    textAlign = TextAlign.Center,
    typography = MaterialTheme.typography.h3,
)

@Composable
internal fun defaultComposeDialogContentStyle() = defaultComposeTextStyle().copy(
    textColor = colorResource(id = R.color.accent_variant),
    textAlign = TextAlign.Center,
    typography = MaterialTheme.typography.body1,
)

@Composable
internal fun defaultComposeDialogPrimaryButtonStyle() = defaultComposeButtonStyle().copy(
    backgroundColor = colorResource(id = R.color.accent),
    verticalPadding = dimensionResource(id = R.dimen.tiny_padding),
    horizontalPadding = dimensionResource(id = R.dimen.medium_padding),
)

@Composable
internal fun defaultComposeDialogSecondaryButtonModel() = outlinedComposeButtonModel().copy(
    backgroundColor = MaterialTheme.colors.primary,
    border = BorderStroke(
        dimensionResource(id = R.dimen.button_border_width),
        colorResource(id = R.color.accent_variant),
    ),
    textColor = colorResource(id = R.color.accent_variant),
    verticalPadding = dimensionResource(id = R.dimen.tiny_padding),
    horizontalPadding = dimensionResource(id = R.dimen.medium_padding),
)

@Composable
internal fun defaultComposeDialogStyle() = ComposeDialogStyle(
    backdropColor = MaterialTheme.colors.surface.copy(alpha = BACKDROP_ALPHA),
    backgroundColor = MaterialTheme.colors.primary,
    shape = MaterialTheme.shapes.medium,
)

internal data class ComposeDialogStyle(
    val backdropColor: Color,
    val backgroundColor: Color,
    val shape: Shape,
)
