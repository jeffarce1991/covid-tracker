package com.jeff.covidtracker.main

import com.jeff.covidtracker.ActivityScope
import com.jeff.covidtracker.main.detail.presenter.CountryDetailPresenterModule
import com.jeff.covidtracker.main.detail.view.CountryDetailActivity
import com.jeff.covidtracker.main.list.presenter.MainPresenterModule
import com.jeff.covidtracker.main.list.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainPresenterModule::class])
    fun mainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [CountryDetailPresenterModule::class])
    fun countryDetailActivity(): CountryDetailActivity
}