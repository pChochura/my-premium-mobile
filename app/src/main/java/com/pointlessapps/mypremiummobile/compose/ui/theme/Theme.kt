package com.pointlessapps.mypremiummobile.compose.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pointlessapps.mypremiummobile.R

private val fontFamily = FontFamily(
    Font(
        resId = R.font.montserrat_light,
        weight = FontWeight.Light,
    ),
    Font(
        resId = R.font.montserrat_normal,
        weight = FontWeight.Normal,
    ),
    Font(
        resId = R.font.montserrat_medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resId = R.font.montserrat_semi_bold,
        weight = FontWeight.SemiBold,
    ),
    Font(
        resId = R.font.montserrat_bold,
        weight = FontWeight.Bold,
    ),
)

@Composable
private fun typography() = Typography(
    defaultFontFamily = fontFamily,
    h1 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
    ),
    button = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
)

@Composable
private fun shapes() = Shapes(
    small = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)),
    medium = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corners)),
)

@Composable
private fun lightColorPalette() = lightColors(
    primary = colorResource(id = R.color.almost_black),
    onPrimary = colorResource(id = R.color.white),
    background = colorResource(id = R.color.light_grey),
    onBackground = colorResource(id = R.color.black),
    surface = colorResource(id = R.color.white),
    onSurface = colorResource(id = R.color.black),
)

@Composable
internal fun ProjectTheme(content: @Composable () -> Unit) {
    val colors = lightColorPalette() // TODO add dark theme

    MaterialTheme(
        colors = colors,
        typography = typography(),
        shapes = shapes(),
        content = content,
    )
}
