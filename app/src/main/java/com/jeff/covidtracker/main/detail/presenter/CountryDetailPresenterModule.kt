package com.jeff.covidtracker.main.detail.presenter

import dagger.Binds
import dagger.Module

@Module
abstract class CountryDetailPresenterModule {

    @Binds
    abstract fun bindCountryDetailPresenter(
        defaultCountryDetailPresenter: DefaultCountryDetailPresenter): CountryDetailPresenter
}