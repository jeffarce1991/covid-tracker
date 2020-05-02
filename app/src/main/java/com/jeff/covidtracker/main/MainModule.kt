package com.jeff.covidtracker.main

import com.jeff.covidtracker.ActivityScope
import com.jeff.covidtracker.main.presenter.MainPresenterModule
import com.jeff.covidtracker.main.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainPresenterModule::class])
    fun mainActivity(): MainActivity
}