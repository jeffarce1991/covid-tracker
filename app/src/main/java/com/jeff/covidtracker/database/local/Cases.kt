package com.jeff.covidtracker.database.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Cases.TABLE_NAME)
data class Cases (

    @PrimaryKey
    @ColumnInfo(name = "country_code")
    var countryCode: String,
    @ColumnInfo(name = "country_name")
    var country: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "country_details")
    var details: CountryDetails? = null,
    @ColumnInfo(name = "new_cases")
    var newCases: NewCases? = null,
    @ColumnInfo(name = "total_cases")
    var totalCases: TotalCases? = null
) {

    data class CountryDetails(
        var province: String? = null,
        var city: String? = null,
        var cityCode: String? = null,
        var lat: String? = null,
        var lon: String? = null)

    companion object {

        const val COLUMN_DEAL_ID = "country_cases_id"
        const val COLUMN_ID = "id"
        const val TABLE_NAME = "country_cases"
    }

}