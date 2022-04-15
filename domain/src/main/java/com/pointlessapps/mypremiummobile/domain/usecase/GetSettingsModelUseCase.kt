package com.pointlessapps.mypremiummobile.domain.usecase

import com.pointlessapps.mypremiummobile.domain.auth.usecase.GetUserNameUseCase
import com.pointlessapps.mypremiummobile.domain.model.SettingsModel
import com.pointlessapps.mypremiummobile.domain.services.usecase.GetUserPhoneNumbersUseCase
import com.pointlessapps.mypremiummobile.domain.settings.usecase.GetNotificationMethodsUseCase
import com.pointlessapps.mypremiummobile.domain.settings.usecase.GetSettingsUseCase
import kotlinx.coroutines.flow.combine

class GetSettingsModelUseCase(
    private val getUserNameUseCase: GetUserNameUseCase,
    private val getUserPhoneNumbersUseCase: GetUserPhoneNumbersUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val getNotificationMethodsUseCase: GetNotificationMethodsUseCase,
) {

    operator fun invoke() = combine(
        getUserNameUseCase(),
        getUserPhoneNumbersUseCase(),
        getSettingsUseCase(),
        getNotificationMethodsUseCase(),
    ) { userInfo, phoneNumbers, settings, notificationMethods ->
        val phoneNumber = requireNotNull(phoneNumbers.find { it.isMain })
        SettingsModel(
            userInfo = userInfo,
            phoneNumber = phoneNumber,
            settings = settings,
            notificationMethods = notificationMethods,
        )
    }
}
