package com.jeff.covidtracker.database.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Cases.TABLE_NAME)
data class Cases constructor(

    @PrimaryKey
    @ColumnInfo(name = "country_code")
    var countryCode: String,
    @ColumnInfo(name = "country_name")
    var country: String,
    @ColumnInfo(name = "province")
    var province: String,
    @ColumnInfo(name = "city")
    var city: String,
    @ColumnInfo(name = "city_code")
    var cityCode: String,
    @ColumnInfo(name = "lat")
    var lat: String,
    @ColumnInfo(name = "lon")
    var lon: String,
    @ColumnInfo(name = "confirmed")
    var confirmed: String,
    @ColumnInfo(name = "deaths")
    var deaths: String,
    @ColumnInfo(name = "recovered")
    var recovered: String,
    @ColumnInfo(name = "active")
    var active: String,
    @ColumnInfo(name = "date")
    var date: String) {

    companion object {

        const val COLUMN_DEAL_ID = "country_cases_id"
        const val COLUMN_ID = "id"
        const val TABLE_NAME = "country_cases"
    }

}