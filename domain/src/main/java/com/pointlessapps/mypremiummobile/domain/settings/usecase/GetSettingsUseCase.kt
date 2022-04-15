package com.pointlessapps.mypremiummobile.domain.settings.usecase

import com.pointlessapps.mypremiummobile.domain.settings.SettingsRepository

class GetSettingsUseCase(
    private val settingsRepository: SettingsRepository,
) {

    operator fun invoke() = settingsRepository.getSettings()
}
