package com.pointlessapps.mypremiummobile.compose.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.pointlessapps.mypremiummobile.R
import com.pointlessapps.mypremiummobile.compose.utils.conditional

@Composable
internal fun ComposeCheckbox(
    checked: Boolean,
    onChecked: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    checkboxStyle: ComposeCheckboxStyle = defaultComposeCheckboxStyle(),
) {
    Box(
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.icon_size))
            .clip(checkboxStyle.backgroundShape)
            .background(checkboxStyle.backgroundColor)
            .conditional(checkboxStyle.enabled) {
                clickable { onChecked(!checked) }
            }
            .then(modifier),
    ) {
        AnimatedVisibility(visible = checked, enter = fadeIn(), exit = fadeOut()) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = null,
                tint = checkboxStyle.foregroundColor,
            )
        }
    }
}

@Composable
internal fun defaultComposeCheckboxStyle() = ComposeCheckboxStyle(
    enabled = true,
    backgroundColor = colorResource(id = R.color.accent_variant),
    foregroundColor = MaterialTheme.colors.primary,
    backgroundShape = CircleShape,
)

internal data class ComposeCheckboxStyle(
    val enabled: Boolean,
    val backgroundColor: Color,
    val foregroundColor: Color,
    val backgroundShape: Shape,
)
