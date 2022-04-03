package com.pointlessapps.mypremiummobile.remote.datasource.services

import com.pointlessapps.mypremiummobile.datasource.services.ServicesDatasource
import com.pointlessapps.mypremiummobile.remote.datasource.services.mapper.toInternetPackageStatus
import com.pointlessapps.mypremiummobile.remote.datasource.services.mapper.toInternetPackages
import com.pointlessapps.mypremiummobile.remote.datasource.services.mapper.toPhoneNumbers
import com.pointlessapps.mypremiummobile.remote.datasource.services.mapper.toUserOffer
import com.pointlessapps.mypremiummobile.remote.datasource.services.service.ServicesService
import java.text.SimpleDateFormat

internal class ServicesDatasourceImpl(
    private val service: ServicesService,
    private val dateParser: SimpleDateFormat,
) : ServicesDatasource {

    override suspend fun getUserPhoneNumbers() =
        service.getUserPhoneNumbers().toPhoneNumbers()

    override suspend fun getUserOffer(phoneNumberId: String) =
        service.getUserOffer(phoneNumberId).toUserOffer()

    override suspend fun getInternetPackageStatus(phoneNumberId: String) =
        service.getInternetPackageStatus(phoneNumberId).toInternetPackageStatus(dateParser)

    override suspend fun getInternetPackages(phoneNumberId: String) =
        service.getInternetPackages(phoneNumberId).toInternetPackages()
}
