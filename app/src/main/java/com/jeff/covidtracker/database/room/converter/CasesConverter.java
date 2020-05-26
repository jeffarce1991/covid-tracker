package com.jeff.covidtracker.database.room.converter;

import androidx.room.TypeConverter;

import com.jeff.covidtracker.database.local.Cases;
import com.jeff.covidtracker.database.local.NewCases;
import com.jeff.covidtracker.database.local.TotalCases;
import com.jeff.covidtracker.utilities.ConverterUtil;

public class CasesConverter {
    private CasesConverter() { }

    @TypeConverter
    public static String fromNewCases(NewCases newCases) {
        return ConverterUtil.serialise(newCases);
    }

    @TypeConverter
    public static NewCases toNewCases(String serialised) {
        return ConverterUtil.deserialise(serialised, NewCases.class);
    }


    @TypeConverter
    public static String fromTotalCases(TotalCases totalCases) {
        return ConverterUtil.serialise(totalCases);
    }

    @TypeConverter
    public static TotalCases toTotalCases(String serialised) {
        return ConverterUtil.deserialise(serialised, TotalCases.class);
    }

    @TypeConverter
    public static String fromCountryDetails(Cases.CountryDetails countryDetails) {
        return ConverterUtil.serialise(countryDetails);
    }

    @TypeConverter
    public static Cases.CountryDetails toCountryDetails(String serialised) {
        return ConverterUtil.deserialise(serialised, Cases.CountryDetails.class);
    }
}
