package com.jeff.covidtracker

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.jeff.covidtracker.database.DatabaseModule
import com.jeff.covidtracker.main.MainModule
import com.jeff.covidtracker.main.list.view.MainActivityTest
import com.jeff.covidtracker.supplychain.SupplyChainModule
import com.jeff.covidtracker.usecase.TestUseCaseModule
import com.jeff.covidtracker.utilities.UtilityModule
import com.jeff.covidtracker.webservices.api.ApiModule
import com.jeff.covidtracker.webservices.internet.RxInternetModule
import com.jeff.covidtracker.webservices.usecase.WebServiceUseCaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Component(modules = [
    AndroidSupportInjectionModule::class,
    TestUseCaseModule::class,
    TestAppModule::class,
    MainModule::class,
    AppModule::class,
    UtilityModule::class,
    DatabaseModule::class,
    ApiModule::class
])
@Singleton
interface TestComponent: AppComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(testApplication: TestApplication): Builder
    fun build(): TestComponent
  }

  fun inject(mainActivityTest: MainActivityTest)

  companion object {
      fun get(): TestComponent {
          return (getInstrumentation()
              .targetContext
              .applicationContext as TestApplication)
              .getComponent()
      }

  }
}