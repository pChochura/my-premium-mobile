package com.pointlessapps.mypremiummobile.domain.utils

class NumberFormatter {

    fun toFloatString(value: String) = toFloatString(toFloat(value))

    fun toFloatString(value: Float) = String.format("%.2f", value).replace('.', ',')

    fun toFloat(value: String) = value.replace(',', '.').toFloat()
}
