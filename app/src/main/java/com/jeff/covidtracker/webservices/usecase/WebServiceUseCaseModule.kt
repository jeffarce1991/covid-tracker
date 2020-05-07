package com.jeff.covidtracker.webservices.usecase

import com.jeff.covidtracker.webservices.usecase.loader.CountryRemoteLoader
import com.jeff.covidtracker.webservices.usecase.loader.DefaultCountryRemoteLoader
import com.jeff.covidtracker.webservices.usecase.loader.DefaultPhotoRemoteLoader
import com.jeff.covidtracker.webservices.usecase.loader.PhotoRemoteLoader
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
    ):
            CountryRemoteLoader
}