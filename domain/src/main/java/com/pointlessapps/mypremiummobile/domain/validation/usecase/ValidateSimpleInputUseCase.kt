package com.pointlessapps.mypremiummobile.domain.validation.usecase

import com.pointlessapps.mypremiummobile.domain.validation.ValidationRepository

class ValidateSimpleInputUseCase(
    private val repository: ValidationRepository,
) {

    fun prepare(input: String?): Boolean =
        repository.isInputNotNullAndBlank(candidate = input)
}
