package com.jeff.covidtracker.main

import com.jeff.covidtracker.ActivityScope
import com.jeff.covidtracker.main.list.presenter.MainPresenterModule
import com.jeff.covidtracker.main.list.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainPresenterModule::class])
    fun mainActivity(): MainActivity
}