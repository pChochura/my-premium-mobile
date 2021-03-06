package com.pointlessapps.mypremiummobile.domain.di

import com.pointlessapps.mypremiummobile.domain.usecase.GetDashboardModelUseCase
import com.pointlessapps.mypremiummobile.domain.usecase.GetPaymentsModelUseCase
import com.pointlessapps.mypremiummobile.domain.usecase.GetSettingsModelUseCase
import org.koin.dsl.module

internal val domainModule = module {
    factory {
        GetPaymentsModelUseCase(
            getUserNameUseCase = get(),
            getUserPhoneNumbersUseCase = get(),
            getPaymentAmountUseCase = get(),
            getInvoicesUseCase = get(),
            getPaymentsUseCase = get(),
            getDeliveryMethodsUseCase = get(),
        )
    }

    factory {
        GetDashboardModelUseCase(
            getUserNameUseCase = get(),
            getUserPhoneNumbersUseCase = get(),
            getUserOfferUseCase = get(),
            getBalanceUseCase = get(),
            getInternetPackageStatusUseCase = get(),
            getInternetPackagesUseCase = get(),
        )
    }

    factory {
        GetSettingsModelUseCase(
            getUserNameUseCase = get(),
            getUserPhoneNumbersUseCase = get(),
            getSettingsUseCase = get(),
            getNotificationMethodsUseCase = get(),
        )
    }
}
