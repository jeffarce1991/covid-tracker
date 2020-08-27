package com.jeff.covidtracker.main.dashboard.presenter

import dagger.Binds
import dagger.Module

@Module
abstract class DashboardPresenterModule {

    @Binds
    abstract fun bindDashboardPresenter(
        defaultDashboardPresenter: DefaultDashboardPresenter): DashboardPresenter
}