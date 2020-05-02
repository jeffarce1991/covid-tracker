package com.jeff.covidtracker.supplychain.photo

import dagger.Binds
import dagger.Module

@Module
abstract class PhotoUseCaseModule {

    @Binds
    abstract fun bindPhotoLoader(defaultPhotoLoader: DefaultPhotoLoader): PhotoLoader
}