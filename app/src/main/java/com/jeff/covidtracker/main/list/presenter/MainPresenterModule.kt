package com.jeff.covidtracker.main.list.presenter

import dagger.Binds
import dagger.Module

@Module
abstract class MainPresenterModule {

    @Binds
    abstract fun bindMainPresenter(
        defaultSplashPresenter: DefaultMainPresenter): MainPresenter
}