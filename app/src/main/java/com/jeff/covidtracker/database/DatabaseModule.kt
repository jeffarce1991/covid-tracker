package com.jeff.covidtracker.database

import android.app.Application
import androidx.room.Room
import com.jeff.covidtracker.R
import com.jeff.covidtracker.database.room.AppDatabase
import com.jeff.covidtracker.database.room.dao.CasesDao
import com.jeff.covidtracker.database.room.dao.CountryDao
import com.jeff.covidtracker.database.room.dao.PhotoDao
import com.jeff.covidtracker.database.usecase.local.LocalUseCaseModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [LocalUseCaseModule::class])
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application,
            AppDatabase::class.java,
            application.getString(R.string.db_name))
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providePhotoDao(appDatabase: AppDatabase): PhotoDao {
        return appDatabase.photoDao()
    }

    @Provides
    @Singleton
    fun providCountryDao(appDatabase: AppDatabase): CountryDao {
        return appDatabase.countryDao()
    }

    @Provides
    @Singleton
    fun providCasesDao(appDatabase: AppDatabase): CasesDao {
        return appDatabase.casesDao()
    }
}