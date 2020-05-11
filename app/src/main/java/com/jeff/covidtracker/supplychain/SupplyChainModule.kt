package com.jeff.covidtracker.supplychain

import com.jeff.covidtracker.supplychain.country.detail.CasesLoader
import com.jeff.covidtracker.supplychain.country.detail.DefaultCasesLoader
import com.jeff.covidtracker.supplychain.country.list.CountryLoader
import com.jeff.covidtracker.supplychain.country.list.DefaultCountryLoader
import com.jeff.covidtracker.supplychain.photo.DefaultPhotoLoader
import com.jeff.covidtracker.supplychain.photo.PhotoLoader
import dagger.Binds
import dagger.Module

@Module
abstract class SupplyChainModule {

    @Binds
    abstract fun bindPhotoLoader(defaultPhotoLoader: DefaultPhotoLoader): PhotoLoader

    @Binds
    abstract fun bindCountryLoader(defaultCountryLoader: DefaultCountryLoader): CountryLoader

    @Binds
    abstract fun bindCasesLoader(defaultCasesLoader: DefaultCasesLoader): CasesLoader
}