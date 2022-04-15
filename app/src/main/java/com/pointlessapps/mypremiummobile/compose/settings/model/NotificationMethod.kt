package com.pointlessapps.mypremiummobile.compose.settings.model

enum class NotificationMethod {
    SMS, EMAIL;

    companion object {
        fun fromString(value: String) = valueOf(value.uppercase())
    }
}
