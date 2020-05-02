package com.jeff.covidtracker.webservices.usecase

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
}