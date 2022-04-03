package com.pointlessapps.mypremiummobile.remote.datasource.services

import com.pointlessapps.mypremiummobile.datasource.services.ServicesDatasource
import com.pointlessapps.mypremiummobile.remote.datasource.services.mapper.toPhoneNumbers
import com.pointlessapps.mypremiummobile.remote.datasource.services.service.ServicesService

internal class ServicesDatasourceImpl(
    private val service: ServicesService,
) : ServicesDatasource {

    override suspend fun getUserPhoneNumbers() =
        service.getUserPhoneNumbers().toPhoneNumbers()
}
