package com.jeff.covidtracker.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jeff.covidtracker.database.local.Country;
import com.jeff.covidtracker.database.local.Photo;
import com.jeff.covidtracker.database.room.converter.CountryConverter;
import com.jeff.covidtracker.database.room.converter.PhotoConverter;
import com.jeff.covidtracker.database.room.dao.CountryDao;
import com.jeff.covidtracker.database.room.dao.PhotoDao;

@Database(
        entities = {
                Photo.class,
                Country.class
        },
        version = 4,
        exportSchema = false
)

@TypeConverters(
        {
                PhotoConverter.class,
                CountryConverter.class
        })
public abstract class AppDatabase extends RoomDatabase {
        public abstract PhotoDao photoDao();
        public abstract CountryDao countryDao();
}
