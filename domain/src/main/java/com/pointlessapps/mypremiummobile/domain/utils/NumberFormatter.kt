package com.pointlessapps.mypremiummobile.domain.utils

class NumberFormatter {

    fun toFloatString(value: Float) = value.toString().replace('.', ',')

    fun toFloat(value: String) = value.replace(',', '.').toFloat()
}
