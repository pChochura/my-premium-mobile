package com.pointlessapps.mypremiummobile.domain.validation

interface ValidationRepository {

    fun isInputNotNullAndBlank(candidate: String?): Boolean
}

internal class ValidationRepositoryImpl : ValidationRepository {
    override fun isInputNotNullAndBlank(candidate: String?) = !candidate.isNullOrBlank()
}
