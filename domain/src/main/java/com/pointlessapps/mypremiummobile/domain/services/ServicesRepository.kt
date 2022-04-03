package com.pointlessapps.mypremiummobile.domain.services

import com.pointlessapps.mypremiummobile.datasource.services.ServicesDatasource
import com.pointlessapps.mypremiummobile.datasource.services.dto.PhoneNumberResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface ServicesRepository {
    fun getUserPhoneNumbers(): Flow<List<PhoneNumberResponse>>
}

internal class ServicesRepositoryImpl(
    private val servicesDatasource: ServicesDatasource,
) : ServicesRepository {

    override fun getUserPhoneNumbers() = flow {
        emit(servicesDatasource.getUserPhoneNumbers())
    }.flowOn(Dispatchers.IO)
}
