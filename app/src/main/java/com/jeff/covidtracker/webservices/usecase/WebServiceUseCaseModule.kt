package com.jeff.covidtracker.webservices.usecase

import com.jeff.covidtracker.webservices.usecase.loader.*
import dagger.Binds
import dagger.Module

@Module
interface WebServiceUseCaseModule {

    @Binds
    fun bindPhotoRemoteLoader(
            defaultPhotoRemoteLoader: DefaultPhotoRemoteLoader):
            PhotoRemoteLoader

    @Binds
    fun bindCountryRemoteLoader(
            defaultCountryRemoteLoader: DefaultCountryRemoteLoader
    ): CountryRemoteLoader

    @Binds
    fun bindCasesRemoteLoader(
            defaultCasesRemoteLoader: DefaultCasesRemoteLoader
    ): CasesRemoteLoader

    @Binds
    fun bindSummaryRemoteLoader(
            defaultSummaryRemoteLoader: DefaultSummaryRemoteLoader
    ): SummaryRemoteLoader
}