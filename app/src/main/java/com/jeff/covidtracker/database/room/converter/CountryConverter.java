package com.jeff.covidtracker.database.room.converter;

import androidx.room.TypeConverter;

import com.jeff.covidtracker.database.local.Country;
import com.jeff.covidtracker.database.local.Photo;
import com.jeff.covidtracker.utilities.ConverterUtil;

public class CountryConverter {
    private CountryConverter() { }

    @TypeConverter
    public static String fromCountry(Country country) {
        return ConverterUtil.serialise(country);
    }

    @TypeConverter
    public static Country toCountry(String serialised) {
        return ConverterUtil.deserialise(serialised, Country.class);
    }
}
