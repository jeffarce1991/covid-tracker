package com.jeff.covidtracker

import android.content.Context
import dagger.Module
import dagger.Provides


@Module
class TestAppModule {
    @Provides
    fun provideContext(application: TestApplication): Context {
        return application.applicationContext
    }
}