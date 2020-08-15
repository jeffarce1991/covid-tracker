package com.jeff.covidtracker.database.usecase.local

import com.jeff.covidtracker.database.usecase.local.loader.*
import com.jeff.covidtracker.database.usecase.local.saver.*
import dagger.Binds
import dagger.Module

@Module
interface LocalUseCaseModule {
    @Binds
    fun bindPhotoLocalLoader(implementation: DefaultPhotoLocalLoader): PhotoLocalLoader

    @Binds
    fun bindPhotoLocalSaver(implementation: DefaultPhotoLocalSaver): PhotoLocalSaver

    @Binds
    fun bindCountryLocalSaver(implementation: DefaultCountryLocalSaver): CountryLocalSaver

    @Binds
    fun bindCountryLocalLoader(implementation: DefaultCountryLocalLoader): CountryLocalLoader

    @Binds
    fun bindCasesLocalSaver(implementation: DefaultCasesLocalSaver): CasesLocalSaver

    @Binds
    fun bindCasesLocalLoader(implementation: DefaultCasesLocalLoader): CasesLocalLoader

}