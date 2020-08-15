package com.jeff.covidtracker.database

import android.app.Application
import androidx.room.Room
import com.jeff.covidtracker.MyApplication
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
    fun provideDatabase(myApplication: MyApplication): AppDatabase {
        return Room.databaseBuilder(myApplication,
            AppDatabase::class.java,
            myApplication.getString(R.string.db_name))
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
    fun provideCountryDao(appDatabase: AppDatabase): CountryDao {
        return appDatabase.countryDao()
    }

    @Provides
    @Singleton
    fun provideCasesDao(appDatabase: AppDatabase): CasesDao {
        return appDatabase.casesDao()
    }
}