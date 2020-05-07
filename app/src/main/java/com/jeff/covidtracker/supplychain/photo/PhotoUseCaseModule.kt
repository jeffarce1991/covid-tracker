package com.jeff.covidtracker.supplychain.photo

import com.jeff.covidtracker.supplychain.country.CountryLoader
import com.jeff.covidtracker.supplychain.country.DefaultCountryLoader
import dagger.Binds
import dagger.Module

@Module
abstract class PhotoUseCaseModule {

    @Binds
    abstract fun bindPhotoLoader(defaultPhotoLoader: DefaultPhotoLoader): PhotoLoader

    @Binds
    abstract fun bindCountryLoader(defaultCountryLoader: DefaultCountryLoader): CountryLoader
}