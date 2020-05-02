package com.jeff.covidtracker

import android.app.Application
import com.jeff.covidtracker.database.DatabaseModule
import com.jeff.covidtracker.webservices.internet.RxInternetModule
import com.jeff.covidtracker.main.MainModule
import com.jeff.covidtracker.supplychain.photo.PhotoUseCaseModule
import com.jeff.covidtracker.utilities.UtilityModule
import com.jeff.covidtracker.webservices.api.ApiModule
import com.jeff.covidtracker.webservices.usecase.WebServiceUseCaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [AndroidSupportInjectionModule::class,
    AndroidSupportInjectionModule::class,
    MainModule::class,
    AppModule::class,
    RxInternetModule::class,
    UtilityModule::class,
    DatabaseModule::class,
    ApiModule::class,
    WebServiceUseCaseModule::class,
    PhotoUseCaseModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(myApplication: MyApplication)
}