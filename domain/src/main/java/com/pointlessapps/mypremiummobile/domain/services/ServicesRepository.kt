package com.pointlessapps.mypremiummobile.domain.services

import com.pointlessapps.mypremiummobile.datasource.services.ServicesDatasource
import com.pointlessapps.mypremiummobile.datasource.services.dto.InternetPackageResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.InternetPackageStatusResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.PhoneNumberResponse
import com.pointlessapps.mypremiummobile.datasource.services.dto.UserOfferResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface ServicesRepository {
    fun getUserPhoneNumbers(): Flow<List<PhoneNumberResponse>>

    fun getUserOffer(phoneNumberId: String): Flow<UserOfferResponse>

    fun getInternetPackageStatus(phoneNumberId: String): Flow<InternetPackageStatusResponse>

    fun getInternetPackages(phoneNumberId: String): Flow<List<InternetPackageResponse>>

    fun buyInternetPackage(phoneNumberId: String, packageId: Int): Flow<Unit>
}

internal class ServicesRepositoryImpl(
    private val servicesDatasource: ServicesDatasource,
) : ServicesRepository {

    override fun getUserPhoneNumbers() = flow {
        emit(servicesDatasource.getUserPhoneNumbers())
    }.flowOn(Dispatchers.IO)

    override fun getUserOffer(phoneNumberId: String) = flow {
        emit(servicesDatasource.getUserOffer(phoneNumberId))
    }.flowOn(Dispatchers.IO)

    override fun getInternetPackageStatus(phoneNumberId: String) = flow {
        emit(servicesDatasource.getInternetPackageStatus(phoneNumberId))
    }.flowOn(Dispatchers.IO)

    override fun getInternetPackages(phoneNumberId: String) = flow {
        emit(servicesDatasource.getInternetPackages(phoneNumberId))
    }.flowOn(Dispatchers.IO)

    override fun buyInternetPackage(phoneNumberId: String, packageId: Int): Flow<Unit> = flow {
        emit(servicesDatasource.buyInternetPackage(phoneNumberId, packageId))
    }.flowOn(Dispatchers.IO)
}
