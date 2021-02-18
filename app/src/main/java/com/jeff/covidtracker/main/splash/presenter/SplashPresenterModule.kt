package com.jeff.covidtracker.main.splash.presenter

import dagger.Binds
import dagger.Module

@Module
abstract class SplashPresenterModule {
    @Binds
    abstract fun bindSplashPresenter(
        defaultSplashPresenter: DefaultSplashPresenter): SplashPresenter
}