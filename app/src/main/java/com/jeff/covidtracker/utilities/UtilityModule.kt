package com.jeff.covidtracker.utilities

import com.jeff.covidtracker.utilities.rx.RxSchedulerUtilsModule
import dagger.Module


@Module(includes = [RxSchedulerUtilsModule::class])
abstract class UtilityModule {

}