package com.pointlessapps.mypremiummobile.compose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.Dp
import com.pointlessapps.mypremiummobile.R
import com.pointlessapps.mypremiummobile.compose.model.InputError
import com.pointlessapps.mypremiummobile.compose.utils.onFocusChanged

@Composable
internal fun ComposeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    onFocusGained: (() -> Unit)? = null,
    onFocusLost: (() -> Unit)? = null,
    onImeAction: ((ImeAction) -> Unit)? = null,
    error: InputError = defaultTextFieldError(),
    labelTextStyle: ComposeTextStyle = defaultTextFieldLabelStyle(),
    errorTextStyle: ComposeTextStyle = defaultTextFieldErrorStyle(),
    textFieldStyle: ComposeTextFieldStyle = defaultComposeTextFieldStyle(),
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)

    ComposeTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValueState = it
            if (value != it.text) {
                onValueChange(it.text)
            }
        },
        label = label,
        modifier = modifier,
        onFocusGained = onFocusGained,
        onFocusLost = onFocusLost,
        onImeAction = onImeAction,
        error = error,
        labelTextStyle = labelTextStyle,
        errorTextStyle = errorTextStyle,
        textFieldStyle = textFieldStyle,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun ComposeTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    onFocusGained: (() -> Unit)? = null,
    onFocusLost: (() -> Unit)? = null,
    onImeAction: ((ImeAction) -> Unit)? = null,
    error: InputError = defaultTextFieldError(),
    labelTextStyle: ComposeTextStyle = defaultTextFieldLabelStyle(),
    errorTextStyle: ComposeTextStyle = defaultTextFieldErrorStyle(),
    textFieldStyle: ComposeTextFieldStyle = defaultComposeTextFieldStyle(),
) {
    var showPassword by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.small_padding),
        ),
    ) {
        ComposeText(
            modifier = Modifier.fillMaxWidth(),
            text = label,
            textStyle = labelTextStyle,
        )
        Surface(
            elevation = textFieldStyle.elevation,
            shape = textFieldStyle.shape,
            color = textFieldStyle.backgroundColor,
        ) {
            if (value.text.isEmpty()) {
                ComposeText(
                    modifier = Modifier
                        .padding(textFieldStyle.padding)
                        .fillMaxWidth(),
                    text = textFieldStyle.placeholder,
                    textStyle = defaultComposeTextStyle().copy(
                        typography = textFieldStyle.textStyle,
                        textColor = textFieldStyle.placeholderColor,
                    ),
                )
            }
            BasicTextField(
                modifier = Modifier
                    .padding(textFieldStyle.padding)
                    .fillMaxWidth()
                    .onFocusChanged(
                        onFocusGained = { onFocusGained?.invoke() },
                        onFocusLost = { onFocusLost?.invoke() },
                    ),
                value = value,
                onValueChange = onValueChange,
                keyboardOptions = textFieldStyle.keyboardOptions,
                keyboardActions = composeTextFieldKeyboardActions {
                    defaultKeyboardAction(it)
                    onImeAction?.invoke(it)
                    if (it == ImeAction.Done) {
                        keyboardController?.hide()
                    }
                },
                visualTransformation = getPasswordVisualTransformation(
                    keyboardOptions = textFieldStyle.keyboardOptions,
                    showPassword = showPassword,
                ),
                textStyle = textFieldStyle.textStyle.copy(
                    textFieldStyle.textColor,
                ),
                cursorBrush = SolidColor(textFieldStyle.cursorColor),
            )

            getPasswordTrailingIcon(
                keyboardOptions = textFieldStyle.keyboardOptions,
                showPassword = showPassword,
                onClick = { showPassword = !showPassword },
            )?.let { icon ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    icon(modifier = Modifier.align(Alignment.CenterEnd))
                }
            }
        }

        ComposeText(
            modifier = Modifier.alpha(if (error.enabled) 1f else 0f),
            text = error.errorMessage,
            textStyle = errorTextStyle,
        )
    }
}

private fun getPasswordVisualTransformation(
    keyboardOptions: KeyboardOptions,
    showPassword: Boolean,
) = when {
    !showPassword && keyboardOptions.keyboardType in listOf(
        KeyboardType.Password, KeyboardType.NumberPassword,
    ) -> PasswordVisualTransformation()
    else -> VisualTransformation.None
}

@Composable
private fun getPasswordTrailingIcon(
    keyboardOptions: KeyboardOptions,
    showPassword: Boolean,
    onClick: () -> Unit,
): (@Composable (modifier: Modifier) -> Unit)? = when (keyboardOptions.keyboardType) {
    KeyboardType.Password, KeyboardType.NumberPassword -> {
        @Composable {
            IconButton(modifier = it, onClick = onClick) {
                Icon(
                    painter = painterResource(
                        id = if (showPassword) {
                            R.drawable.ic_visibility
                        } else {
                            R.drawable.ic_visibility_off
                        },
                    ),
                    tint = colorResource(id = R.color.grey),
                    contentDescription = stringResource(id = R.string.toggle_password_visibility),
                )
            }
        }
    }
    else -> null
}

private fun composeTextFieldKeyboardActions(onAny: KeyboardActionScope.(ImeAction) -> Unit) =
    KeyboardActions(
        onDone = { onAny(ImeAction.Done) },
        onGo = { onAny(ImeAction.Go) },
        onNext = { onAny(ImeAction.Next) },
        onPrevious = { onAny(ImeAction.Previous) },
        onSearch = { onAny(ImeAction.Search) },
        onSend = { onAny(ImeAction.Search) },
    )

@Composable
internal fun defaultComposeTextFieldStyle() = ComposeTextFieldStyle(
    keyboardOptions = KeyboardOptions(
        capitalization = KeyboardCapitalization.Sentences,
    ),
    placeholder = "",
    textStyle = MaterialTheme.typography.body1,
    textColor = MaterialTheme.colors.onSurface,
    backgroundColor = MaterialTheme.colors.surface,
    placeholderColor = colorResource(id = R.color.grey),
    cursorColor = colorResource(id = R.color.accent),
    shape = MaterialTheme.shapes.medium,
    elevation = dimensionResource(id = R.dimen.default_elevation),
    padding = PaddingValues(dimensionResource(id = R.dimen.medium_padding)),
)

@Composable
internal fun defaultTextFieldLabelStyle() = defaultComposeTextStyle().copy(
    textColor = MaterialTheme.colors.onSurface,
    typography = MaterialTheme.typography.caption,
)

@Composable
internal fun defaultTextFieldErrorStyle() = defaultComposeTextStyle().copy(
    textColor = colorResource(id = R.color.accent),
    typography = MaterialTheme.typography.caption,
)

@Composable
internal fun defaultTextFieldError() = InputError(
    enabled = false,
    errorMessage = stringResource(id = R.string.the_provided_value_is_incorrect),
)

internal data class ComposeTextFieldStyle(
    val keyboardOptions: KeyboardOptions,
    val placeholder: String,
    val textStyle: TextStyle,
    val textColor: Color,
    val backgroundColor: Color,
    val placeholderColor: Color,
    val cursorColor: Color,
    val shape: Shape,
    val elevation: Dp,
    val padding: PaddingValues,
)
