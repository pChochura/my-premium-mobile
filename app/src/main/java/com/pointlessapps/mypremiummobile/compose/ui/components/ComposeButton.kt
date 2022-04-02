package com.pointlessapps.mypremiummobile.compose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.ButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.pointlessapps.mypremiummobile.R

@Composable
internal fun ComposeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonStyle: ComposeButtonStyle = defaultComposeButtonStyle(),
) {
    Button(
        modifier = modifier,
        enabled = buttonStyle.enabled,
        elevation = buttonStyle.elevation,
        shape = buttonStyle.shape,
        colors = buttonColors(backgroundColor = buttonStyle.backgroundColor),
        border = buttonStyle.border,
        contentPadding = PaddingValues(
            vertical = buttonStyle.verticalPadding,
            horizontal = buttonStyle.horizontalPadding,
        ),
        onClick = onClick,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.button_padding_horizontal),
            ),
        ) {
            ComposeText(
                text = text,
                textStyle = defaultComposeTextStyle().copy(
                    typography = buttonStyle.textStyle,
                    textColor = buttonStyle.textColor,
                ),
            )
        }
    }
}

@Composable
internal fun defaultComposeButtonStyle() = ComposeButtonStyle(
    enabled = true,
    border = null,
    backgroundColor = MaterialTheme.colors.primary,
    textColor = MaterialTheme.colors.onPrimary,
    textStyle = MaterialTheme.typography.button,
    shape = MaterialTheme.shapes.medium,
    verticalPadding = dimensionResource(id = R.dimen.button_padding_vertical),
    horizontalPadding = dimensionResource(id = R.dimen.button_padding_horizontal),
    elevation = ButtonDefaults.elevation(
        disabledElevation = dimensionResource(id = R.dimen.button_default_elevation),
        defaultElevation = dimensionResource(id = R.dimen.button_default_elevation),
        pressedElevation = dimensionResource(id = R.dimen.button_pressed_elevation),
        hoveredElevation = dimensionResource(id = R.dimen.button_pressed_elevation),
        focusedElevation = dimensionResource(id = R.dimen.button_pressed_elevation),
    ),
)

@Composable
internal fun outlinedComposeButtonModel() = ComposeButtonStyle(
    enabled = true,
    border = BorderStroke(
        dimensionResource(id = R.dimen.button_border_width),
        MaterialTheme.colors.primary,
    ),
    backgroundColor = MaterialTheme.colors.background,
    textColor = MaterialTheme.colors.primary,
    textStyle = MaterialTheme.typography.button,
    shape = MaterialTheme.shapes.medium,
    verticalPadding = dimensionResource(id = R.dimen.button_padding_vertical),
    horizontalPadding = dimensionResource(id = R.dimen.button_padding_horizontal),
    elevation = ButtonDefaults.elevation(
        disabledElevation = dimensionResource(id = R.dimen.button_default_elevation),
        defaultElevation = dimensionResource(id = R.dimen.button_default_elevation),
        pressedElevation = dimensionResource(id = R.dimen.button_pressed_elevation),
        hoveredElevation = dimensionResource(id = R.dimen.button_pressed_elevation),
        focusedElevation = dimensionResource(id = R.dimen.button_pressed_elevation),
    ),
)

internal data class ComposeButtonStyle(
    val enabled: Boolean,
    val border: BorderStroke?,
    val backgroundColor: Color,
    val textColor: Color,
    val textStyle: TextStyle,
    val shape: Shape,
    val verticalPadding: Dp,
    val horizontalPadding: Dp,
    val elevation: ButtonElevation,
)
